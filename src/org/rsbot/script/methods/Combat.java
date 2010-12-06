package org.rsbot.script.methods;

import org.rsbot.script.wrappers.RSComponent;

import java.util.ArrayList;

/**
 * Combat related operations.
 */
public class Combat extends MethodProvider {

	/**
	 * Modern prayers.
	 */
	public static enum Prayer {

		THICK_SKIN(0, 1),
		BURST_OF_STRENGTH(1, 4),
		CLARITY_OF_THOUGHT(2, 7),
		SHARP_EYE(3, 8),
		MYSTIC_WILL(4, 9),
		ROCK_SKIN(5, 10),
		SUPERHUMAN_STRENGTH(6, 13),
		IMPROVED_REFLEXES(7, 16),
		RAPID_RESTORE(8, 19),
		RAPID_HEAL(9, 22),
		PROTECT_ITEM(10, 25),
		HAWK_EYE(11, 26),
		MYSTIC_LORE(12, 27),
		STEEL_SKIN(13, 28),
		ULTIMATE_STRENGTH(14, 31),
		INCREDIBLE_REFLEXES(15, 34),
		PROTECT_FROM_SUMMONING(16, 35),
		PROTECT_FROM_MAGIC(17, 37),
		PROTECT_FROM_MISSILES(18, 40),
		PROTECT_FROM_MELEE(19, 43),
		EAGLE_EYE(20, 44),
		MYSTIC_MIGHT(21, 45),
		RETRIBUTION(22, 46),
		REDEMPTION(23, 49),
		SMITE(24, 52),
		CHIVALRY(25, 60),
		RAPID_RENEWAL(26, 65),
		PIETY(27, 70),
		RIGOUR(28, 74),
		AUGURY(29, 77);

		private int index;
		private int level;

		Prayer(int index, int level) {
			this.index = index;
			this.level = level;
		}

		public int getIndex() {
			return index;
		}

		public int getRequiredLevel() {
			return level;
		}

	}

	public Combat(MethodContext ctx) {
		super(ctx);
	}

	/**
	 * Turns auto-retaliate on or off in the combat tab.
	 *
	 * @param enable <tt>true</tt> to enable; <tt>false</tt> to disable.
	 */
	public void setAutoRetaliate(final boolean enable) {
		final RSComponent autoRetal = methods.interfaces.getComponent(884, 15);
		if (isAutoRetaliateEnabled() != enable) {
			if (methods.game.getCurrentTab() != Game.TAB_ATTACK) {
				methods.game.openTab(Game.TAB_ATTACK);
			}
			if (methods.game.getCurrentTab() == Game.TAB_ATTACK
					&& autoRetal != null) {
				autoRetal.doClick();
			}
		}
	}

	/**
	 * Returns whether or not the auto-retaliate option is enabled.
	 *
	 * @return <tt>true</tt> if retaliate is enabled; otherwise <tt>false</tt>.
	 */
	public boolean isAutoRetaliateEnabled() {
		return methods.settings.getSetting(172) == 0;
	}


	/**
	 * Gets the attack mode.
	 *
	 * @return The current fight mode setting.
	 */
	public int getFightMode() {
		return methods.settings.getSetting(Settings.SETTING_COMBAT_STYLE);
	}

	/**
	 * Sets the attack mode.
	 *
	 * @param fightMode The fight mode to set it to. From 0-3 corresponding to the
	 *                  4 attacking modes.
	 * @see #getFightMode()
	 */
	public void setFightMode(int fightMode) {
		if (fightMode != getFightMode()) {
			methods.game.openTab(Game.TAB_ATTACK);
			if (fightMode == 0) {
				methods.mouse.click(577, 253, 55, 35, true);
			} else if (fightMode == 1) {
				methods.mouse.click(661, 253, 55, 35, true);
			} else if (fightMode == 2) {
				methods.mouse.click(576, 306, 55, 35, true);
			} else if (fightMode == 3) {
				methods.mouse.click(662, 308, 55, 35, true);
			}
		}
	}

	/**
	 * Gets the current Wilderness Level. Written by Speed.
	 *
	 * @return The current wilderness level otherwise, 0.
	 */
	public int getWildernessLevel() {
		return methods.interfaces.get(381).getComponent(1).isValid() ?
				Integer.parseInt(methods.interfaces.get(381).getComponent(1).getText().replace("Level: ", "").trim()) : 0;
	}

	/**
	 * Gets the current player's life points.
	 *
	 * @return The current life points if the interface
	 *         is valid; otherwise 0.
	 */
	public int getLifePoints() {
		try {
			return Integer.parseInt(methods.interfaces.get(748).getComponent(8).getText());
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/**
	 * Returns true if designated prayer is turned on.
	 *
	 * @param prayer The prayer to check.
	 * @return <tt>true</tt> if enabled; otherwise <tt>false</tt>.
	 */
	public boolean isPrayerOn(Prayer prayer) {
		RSComponent[] prayers = methods.interfaces.getComponent(271, 7).getComponents();
		for (RSComponent c : prayers) {
			if (c.getComponentIndex() == prayer.getIndex() && c.getBackgroundColor() != -1)
				return true;
		}
		return false;
	}

	/**
	 * Returns true if the quick prayer interface has been used to activate prayers.
	 *
	 * @return <tt>true</tt> if quick prayer is on; otherwise <tt>false</tt>.
	 */
	public boolean isQuickPrayerOn() {
		return methods.interfaces.getComponent(Game.INTERFACE_PRAYER_ORB, 2).getBackgroundColor() == 782;
	}

	/**
	 * Activates/deactivates a prayer via interfaces.
	 *
	 * @param prayer The prayer to activate.
	 * @param activate <tt>true</tt> to activate; <tt>false</tt> to deactivate.
	 * @return <tt>true</tt> if the interface was clicked; otherwise <tt>false</tt>.
	 */
	public boolean setPrayer(Prayer prayer, boolean activate) {
		methods.game.openTab(Game.TAB_PRAYER);
		return methods.interfaces.getComponent(271, 7).getComponents()[prayer.getIndex()].getBackgroundColor() == -1 &&
				methods.interfaces.getComponent(271, 7).getComponents()[prayer.getIndex()].doAction(activate ? "Activate" : "Deactivate");
	}

	/**
	 * Returns an array of RSComponents representing the prayers that
	 * are selected.
	 *
	 * @return An <code>RSComponent</code> array containing all the
	 *         components that represent selected prayers.
	 */
	public RSComponent[] getSelectedPrayers() {
		ArrayList<RSComponent> selected = new ArrayList<RSComponent>();
		RSComponent[] prayers = methods.interfaces.getComponent(271, 7).getComponents();
		for (RSComponent prayer : prayers) {
			if (prayer.getBackgroundColor() != -1) {
				selected.add(prayer);
			}
		}
		return selected.toArray(new RSComponent[selected.size()]);
	}

	/**
	 * Returns whether or not we're poisoned.
	 *
	 * @return <tt>true</tt> if poisoned; otherwise <tt>false</tt>.
	 */
	public boolean isPoisoned() {
		return methods.settings.getSetting(102) > 0;
	}

	/**
	 * Returns whether or not the special-attack option is enabled.
	 *
	 * @return <tt>true</tt> if special is enabled; otherwise <tt>false</tt>.
	 */
	public boolean isSpecialEnabled() {
		return methods.settings.getSetting(Settings.SETTING_SPECIAL_ATTACK_ENABLED) == 1;
	}

	/**
	 * Gets the special bar energy amount.
	 *
	 * @return The current spec energy.
	 */
	public int getSpecialBarEnergy() {
		return methods.settings.getSetting(300);
	}

	/**
	 * Gets the current player's prayer points.
	 *
	 * @return The current prayer points if the interface
	 *         is valid; otherwise 0.
	 */
	public int getPrayerPoints() {
		try {
			return Integer.parseInt(methods.interfaces.get(Game.INTERFACE_PRAYER_ORB).getComponent(4).getText().trim());
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/**
	 * Gets the current player's health as a percentage of full health.
	 *
	 * @return The current percentage health remaining.
	 */
	public int getHealth() {
		return ((getLifePoints() * 10) / methods.skills.getRealLevel(Skills.CONSTITUTION));
	}

}
