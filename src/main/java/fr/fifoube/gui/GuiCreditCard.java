/*******************************************************************************
 *******************************************************************************/
package fr.fifoube.gui;

import fr.fifoube.gui.components.MaskedPinEditBox;
import fr.fifoube.gui.utilities.GuiText;
import fr.fifoube.items.ItemsRegistery;
import fr.fifoube.main.atm.AtmService;
import fr.fifoube.main.capabilities.CapabilityMoney;
import fr.fifoube.main.economy.EconomyClientConfig;
import fr.fifoube.main.economy.MoneyFormat;
import fr.fifoube.main.economy.PinUtil;
import fr.fifoube.main.economy.TransactionRecord;
import fr.fifoube.packets.ClientEconomyData;
import fr.fifoube.packets.PacketCardChange;
import fr.fifoube.packets.PacketRequestEconomyConfig;
import fr.fifoube.packets.PacketRequestTransactionHistory;
import fr.fifoube.packets.PacketsRegistery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@OnlyIn(Dist.CLIENT)
public class GuiCreditCard extends Screen {

	private static final int PANEL_WIDTH = 280;
	private static final int PANEL_HEIGHT = 230;
	private static final int HISTORY_LINES = 4;
	private static final int TEXT_COLOR = 0xFFFFFF;
	private static final int MUTED_COLOR = 0xCFCFCF;
	private static final int ACCENT_COLOR = 0xFFE066;

	private EditBox amountField;
	private EditBox pinField;
	private Button depositButton;
	private Button withdrawButton;
	private Button confirmYesButton;
	private Button confirmNoButton;

	private long funds;
	private String name = "";
	private boolean pinRequired;
	private boolean awaitingConfirm;
	private long pendingWithdrawAmount;

	private int panelLeft;
	private int panelTop;

	public GuiCreditCard() {
		super(Component.translatable("gui.creditcard"));
	}

	@Override
	protected void init() {
		super.init();
		Player player = this.minecraft.player;
		if (player != null) {
			this.name = player.getDisplayName().getString();
			this.pinRequired = findOwnedCard(player).map(PinUtil::isPinEnabled).orElse(false);
		}
		PacketsRegistery.CHANNEL.sendToServer(new PacketRequestTransactionHistory());
		PacketsRegistery.CHANNEL.sendToServer(new PacketRequestEconomyConfig());

		this.panelLeft = (this.width - PANEL_WIDTH) / 2;
		this.panelTop = (this.height - PANEL_HEIGHT) / 2;

		int left = this.panelLeft + 16;
		int row1 = this.panelTop + 58;
		int row2 = this.panelTop + 84;
		int row3 = this.panelTop + 110;
		int row4 = this.panelTop + 136;

		this.amountField = new EditBox(this.font, left, row1, 120, 18, Component.translatable("title.amountInput"));
		this.amountField.setMaxLength(12);
		this.amountField.setFilter(text -> text.isEmpty() || text.matches("\\d+"));
		this.amountField.setHint(Component.translatable("title.amountInput"));
		this.addRenderableWidget(this.amountField);

		if (this.pinRequired) {
			MaskedPinEditBox pinBox = new MaskedPinEditBox(this.font, left + 136, row1, 56, 18, Component.translatable("title.pinInput"));
			pinBox.setMaxLength(4);
			pinBox.setHint(Component.translatable("title.pinInput"));
			this.pinField = pinBox;
			this.addRenderableWidget(this.pinField);
		}

		this.depositButton = this.addRenderableWidget(Button.builder(Component.translatable("title.deposit"), button -> submit(true))
				.pos(left, row2).size(118, 20).build());
		this.withdrawButton = this.addRenderableWidget(Button.builder(Component.translatable("title.withdraw"), button -> submit(false))
				.pos(left + 124, row2).size(118, 20).build());

		this.addRenderableWidget(Button.builder(Component.translatable("title.quick10Deposit"), b -> quickDeposit(0.10))
				.pos(left, row3).size(76, 18).build());
		this.addRenderableWidget(Button.builder(Component.translatable("title.quick50Deposit"), b -> quickDeposit(0.50))
				.pos(left + 82, row3).size(76, 18).build());
		this.addRenderableWidget(Button.builder(Component.translatable("title.quickMaxDeposit"), b -> quickDeposit(1.0))
				.pos(left + 164, row3).size(76, 18).build());

		this.addRenderableWidget(Button.builder(Component.translatable("title.quick10Withdraw"), b -> quickWithdraw(0.10))
				.pos(left, row4).size(76, 18).build());
		this.addRenderableWidget(Button.builder(Component.translatable("title.quick50Withdraw"), b -> quickWithdraw(0.50))
				.pos(left + 82, row4).size(76, 18).build());
		this.addRenderableWidget(Button.builder(Component.translatable("title.quickMaxWithdraw"), b -> quickWithdraw(1.0))
				.pos(left + 164, row4).size(76, 18).build());

		this.confirmYesButton = Button.builder(Component.translatable("title.yes"), b -> confirmWithdraw(true))
				.pos(this.panelLeft + PANEL_WIDTH / 2 - 62, row2).size(58, 20).build();
		this.confirmNoButton = Button.builder(Component.translatable("title.no"), b -> confirmWithdraw(false))
				.pos(this.panelLeft + PANEL_WIDTH / 2 + 4, row2).size(58, 20).build();
		this.confirmYesButton.visible = false;
		this.confirmNoButton.visible = false;
		this.addRenderableWidget(this.confirmYesButton);
		this.addRenderableWidget(this.confirmNoButton);

		this.setInitialFocus(this.amountField);
		updateConfirmVisibility();
	}

	private void updateConfirmVisibility() {
		boolean confirmVisible = this.awaitingConfirm;
		this.confirmYesButton.visible = confirmVisible;
		this.confirmNoButton.visible = confirmVisible;
		this.depositButton.visible = !confirmVisible;
		this.withdrawButton.visible = !confirmVisible;
		this.amountField.setEditable(!confirmVisible);
		if (this.pinField != null) {
			this.pinField.setEditable(!confirmVisible);
		}
	}

	private static Optional<ItemStack> findOwnedCard(Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.getItem() == ItemsRegistery.CREDITCARD.get()
					&& stack.hasTag()
					&& stack.getTag().getBoolean("Owned")
					&& player.getStringUUID().equals(stack.getTag().getString("OwnerUUID"))) {
				return Optional.of(stack);
			}
		}
		return Optional.empty();
	}

	private void quickDeposit(double ratio) {
		if (this.minecraft.player == null) {
			return;
		}
		long max = AtmService.getInventoryBillValue(this.minecraft.player);
		long amount = ratio >= 1.0 ? max : Math.max(1, (long) Math.floor(max * ratio));
		if (amount <= 0 || !AtmService.isValidAmount(amount)) {
			showInvalidAmount();
			return;
		}
		sendTransaction(amount, true, false);
	}

	private void quickWithdraw(double ratio) {
		EconomyClientConfig config = ClientEconomyData.getConfig();
		long max = config.maxWithdrawableBalance(this.funds);
		long amount = ratio >= 1.0 ? max : Math.max(1, (long) Math.floor(max * ratio));
		if (amount <= 0 || !AtmService.isValidAmount(amount)) {
			showInvalidAmount();
			return;
		}
		this.amountField.setValue(String.valueOf(amount));
		submit(false);
	}

	private void submit(boolean deposit) {
		OptionalLong amount = parseAmount(this.amountField.getValue());
		if (amount.isEmpty() || !AtmService.isValidAmount(amount.getAsLong())) {
			showInvalidAmount();
			return;
		}
		if (!deposit && amount.getAsLong() >= ClientEconomyData.getConfig().atmConfirmThreshold() && !this.awaitingConfirm) {
			this.pendingWithdrawAmount = amount.getAsLong();
			this.awaitingConfirm = true;
			updateConfirmVisibility();
			return;
		}
		sendTransaction(amount.getAsLong(), deposit, !deposit && this.awaitingConfirm);
		if (this.awaitingConfirm) {
			this.awaitingConfirm = false;
			this.pendingWithdrawAmount = 0;
			updateConfirmVisibility();
		}
	}

	private void confirmWithdraw(boolean confirmed) {
		if (!confirmed) {
			this.awaitingConfirm = false;
			this.pendingWithdrawAmount = 0;
			updateConfirmVisibility();
			return;
		}
		sendTransaction(this.pendingWithdrawAmount, false, true);
		this.awaitingConfirm = false;
		this.pendingWithdrawAmount = 0;
		updateConfirmVisibility();
	}

	private void sendTransaction(long amount, boolean deposit, boolean confirmed) {
		String pin = this.pinField != null ? this.pinField.getValue() : "";
		PacketsRegistery.CHANNEL.sendToServer(new PacketCardChange(amount, deposit, pin, confirmed));
	}

	private void showInvalidAmount() {
		if (this.minecraft.player != null) {
			this.minecraft.player.displayClientMessage(Component.translatable("title.invalidAmount"), true);
		}
	}

	private static OptionalLong parseAmount(String text) {
		if (text == null || text.isBlank()) {
			return OptionalLong.empty();
		}
		try {
			return OptionalLong.of(Long.parseLong(text.trim()));
		} catch (NumberFormatException e) {
			return OptionalLong.empty();
		}
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) {
			if (this.awaitingConfirm) {
				confirmWithdraw(false);
				return true;
			}
			this.onClose();
			return true;
		}
		if (keyCode == 257 || keyCode == 335) {
			submit(true);
			return true;
		}
		if (this.pinField != null && this.pinField.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		}
		return this.amountField.keyPressed(keyCode, scanCode, modifiers)
				|| super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		this.amountField.tick();
		if (this.pinField != null) {
			this.pinField.tick();
		}
		Player playerIn = Minecraft.getInstance().player;
		if (playerIn != null) {
			playerIn.getCapability(CapabilityMoney.MONEY_CAPABILITY, null)
					.ifPresent(data -> this.funds = MoneyFormat.toWhole(data.getMoney()));
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		guiGraphics.fill(this.panelLeft, this.panelTop, this.panelLeft + PANEL_WIDTH, this.panelTop + PANEL_HEIGHT, 0xE0101010);
		guiGraphics.fill(this.panelLeft, this.panelTop, this.panelLeft + PANEL_WIDTH, this.panelTop + 2, ACCENT_COLOR);
		guiGraphics.fill(this.panelLeft, this.panelTop + PANEL_HEIGHT - 2, this.panelLeft + PANEL_WIDTH, this.panelTop + PANEL_HEIGHT, ACCENT_COLOR);

		super.render(guiGraphics, mouseX, mouseY, partialTicks);

		int left = this.panelLeft + 16;
		GuiText.draw(this.font, guiGraphics, this.title, left, this.panelTop + 10, ACCENT_COLOR);
		GuiText.draw(this.font, guiGraphics, Component.translatable("title.ownerCard", name), left, this.panelTop + 24, TEXT_COLOR);
		GuiText.draw(this.font, guiGraphics, Component.translatable("title.fundsCard", MoneyFormat.display(this.funds)), left, this.panelTop + 36, TEXT_COLOR);

		renderWithdrawFeeSection(guiGraphics, left);

		if (this.awaitingConfirm) {
			EconomyClientConfig config = ClientEconomyData.getConfig();
			if (config.areAtmFeesEnabled()) {
				long fee = config.calculateFee(this.pendingWithdrawAmount);
				long total = this.pendingWithdrawAmount + fee;
				GuiText.drawCentered(this.font, guiGraphics,
						Component.translatable("title.withdrawConfirmWithFee",
								MoneyFormat.display(this.pendingWithdrawAmount),
								MoneyFormat.display(fee),
								MoneyFormat.display(total)),
						this.panelLeft + PANEL_WIDTH / 2, this.panelTop + 88, ACCENT_COLOR);
			} else {
				GuiText.drawCentered(this.font, guiGraphics,
						Component.translatable("title.withdrawConfirm", MoneyFormat.display(this.pendingWithdrawAmount)),
						this.panelLeft + PANEL_WIDTH / 2, this.panelTop + 92, ACCENT_COLOR);
			}
		}

		GuiText.draw(this.font, guiGraphics, Component.translatable("title.historyHeader"), left, this.panelTop + 162, MUTED_COLOR);
		List<TransactionRecord> history = ClientEconomyData.getHistory();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		for (int i = 0; i < Math.min(HISTORY_LINES, history.size()); i++) {
			TransactionRecord record = history.get(i);
			String time = format.format(new Date(record.timestamp()));
			Component line = Component.translatable("title.historyLine",
					time,
					Component.translatable("title.transaction." + record.type().getId()),
					MoneyFormat.display(record.amount()),
					record.detail() == null || record.detail().isBlank() ? "-" : record.detail());
			GuiText.draw(this.font, guiGraphics, line, left, this.panelTop + 174 + i * 10, MUTED_COLOR);
		}
	}

	private void renderWithdrawFeeSection(GuiGraphics guiGraphics, int left) {
		EconomyClientConfig config = ClientEconomyData.getConfig();
		if (!config.areAtmFeesEnabled()) {
			return;
		}
		long feePercent = (long) config.effectiveAtmWithdrawFeePercent();
		OptionalLong amount = parseAmount(this.amountField.getValue());
		if (amount.isPresent() && amount.getAsLong() > 0 && AtmService.isValidAmount(amount.getAsLong())) {
			long withdrawAmount = amount.getAsLong();
			long fee = config.calculateFee(withdrawAmount);
			long total = withdrawAmount + fee;
			GuiText.draw(this.font, guiGraphics, Component.translatable("title.atmFeePreview",
					MoneyFormat.display(withdrawAmount),
					MoneyFormat.display(fee),
					MoneyFormat.display(total)), left, this.panelTop + 48, MUTED_COLOR);
		} else {
			GuiText.draw(this.font, guiGraphics, Component.translatable("title.atmFeeInfo", feePercent),
					left, this.panelTop + 48, MUTED_COLOR);
		}
	}
}
