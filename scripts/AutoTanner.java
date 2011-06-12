import org.rsbot.event.events.MessageEvent;
import org.rsbot.event.listeners.MessageListener;
import org.rsbot.event.listeners.PaintListener;
import org.rsbot.script.Script;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.methods.Methods;
import org.rsbot.script.util.Timer;
import org.rsbot.script.wrappers.*;

import java.awt.*;
import java.util.HashMap;

@ScriptManifest(authors = {"LastCoder"}, keywords = {"Crafting"}, name = "ArbiTannerLite", version = 2.0, description = "Start, all options are in GUI.")
public class AutoTanner extends Script implements MessageListener,
		PaintListener {

	class Gui extends javax.swing.JFrame {

		/**
		 * GUI
		 */
		private static final long serialVersionUID = 1L;
		public HashMap<String, Hide> hideOptMap = new HashMap<String, Hide>();
		public String[] hideOpt = new String[AutoTanner.HIDE_ARRAY.length];

		// Variables declaration - do not modify
		private javax.swing.JButton jButton1;

		private javax.swing.JComboBox jComboBox1;

		private javax.swing.JLabel jLabel1;

		private javax.swing.JLabel jLabel3;

		// End of variables declaration

		/**
		 * Creates new form Gui
		 */
		public Gui() {
			initComponents();
		}

		/**
		 * This method is called from within the constructor to initialize the
		 * form. WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		// <editor-fold defaultstate="collapsed" desc="Generated Code">
		private void initComponents() {
			gui_on = true;
			for (int i = 0; i < AutoTanner.HIDE_ARRAY.length; i++) {
				hideOpt[i] = AutoTanner.HIDE_ARRAY[i].name;
				hideOptMap.put(hideOpt[i], AutoTanner.HIDE_ARRAY[i]);
			}
			jLabel1 = new javax.swing.JLabel();
			jLabel3 = new javax.swing.JLabel();
			jButton1 = new javax.swing.JButton();
			jComboBox1 = new javax.swing.JComboBox();

			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

			jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
			jLabel1.setText("AutoTanner");

			jLabel3.setText("Which Hide:");

			jButton1.setText("START");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(final java.awt.event.ActionEvent evt) {
					jButton1ActionPerformed(evt);
				}
			});

			jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(hideOpt));

			final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(103, 103, 103).addComponent(jLabel1)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
			layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(27, 27, 27).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(37, 37, 37).addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

			pack();
		}// </editor-fold>

		private void jButton1ActionPerformed(
				final java.awt.event.ActionEvent evt) {
			setVisible(false);
			hide = hideOptMap.get(jComboBox1.getSelectedItem().toString());
			gui_on = false;
		}

	}

	static class Hide {
		public String name;
		public int ind_id;
		public int tanned_id;
		public int component_id;

		public Hide(final String name, final int ind_id, final int tanned_id,
		            final int component_id) {
			this.name = name;
			this.ind_id = ind_id;
			this.component_id = component_id;
			this.tanned_id = tanned_id;
		}
	}

	public enum state {
		BANK, WALK_TO, TRADE_TANNER, INTERFACE, WALK_FROM, REST, TURN_ON_RUN
	}

	private static final Hide[] HIDE_ARRAY = new Hide[]{
			new Hide("Cow Hide", 1739, 1741, 2),
			new Hide("Green Dragon Hide", 1753, 1745, 5),
			new Hide("Red Dragon Hide", 1749, 2507, 7),
			new Hide("Blue Dragon Hide", 1751, 2505, 6),
			new Hide("Black Dragon Hide", 1747, 2509, 8)};
	private static final Color COLOR_1 = new Color(0, 0, 0, 155);
	private static final Color COLOR_2 = new Color(0, 0, 0);
	private static final Color COLOR_3 = new Color(255, 255, 255);
	private static final BasicStroke STROKE = new BasicStroke(1);
	private static final Font FONT_1 = new Font("Arial", 0, 17);

	private static final Font FONT_2 = new Font("Arial", 0, 9);
	private static final int TANNER = 2824;
	private static final RSTile[] OLD_PATH = new RSTile[]{
			new RSTile(3269, 3166), new RSTile(3270, 3166),
			new RSTile(3271, 3166), new RSTile(3272, 3166),
			new RSTile(3273, 3166), new RSTile(3274, 3167),
			new RSTile(3274, 3167), new RSTile(3274, 3168),
			new RSTile(3274, 3170), new RSTile(3274, 3170),
			new RSTile(3275, 3171), new RSTile(3275, 3171),
			new RSTile(3275, 3172), new RSTile(3275, 3173),
			new RSTile(3275, 3173), new RSTile(3277, 3179),
			new RSTile(3278, 3180), new RSTile(3278, 3180),
			new RSTile(3279, 3181), new RSTile(3279, 3181),
			new RSTile(3280, 3182), new RSTile(3281, 3183),
			new RSTile(3281, 3183), new RSTile(3281, 3184),
			new RSTile(3281, 3185), new RSTile(3281, 3186),
			new RSTile(3281, 3186), new RSTile(3281, 3187),
			new RSTile(3281, 3190), new RSTile(3280, 3191),
			new RSTile(3278, 3191), new RSTile(3276, 3192)};
	private RSTilePath current_path;
	private RSTilePath path_back;
	private Hide hide;
	private boolean gui_on = false;
	private int profit;

	private int profitMade;

	private int made;

	private long startTime;

	public boolean atBank() {
		final RSArea a = new RSArea(new RSTile(3264, 3159), new RSTile(3274, 3174));
		return a.contains(getMyPlayer().getLocation());
	}

	public boolean atTanner() {
		final RSArea a = new RSArea(new RSTile(3270, 3189), new RSTile(3277, 3195));
		return a.contains(getMyPlayer().getLocation());
	}

	public state getState() {
		final RSInterface inter = interfaces.get(324);
		if (inter.isValid()) {
			return state.INTERFACE;
		} else if (walking.getEnergy() > Methods.random(80, 100)
				&& !walking.isRunEnabled()) {
			return state.TURN_ON_RUN;
		} else if (atBank()) {
			if (inventory.contains(hide.ind_id)) {
				return state.WALK_TO;
			} else {
				return state.BANK;
			}
		} else if (atTanner()) {
			if (inventory.contains(hide.ind_id)) {
				return state.TRADE_TANNER;
			} else {
				return state.WALK_FROM;
			}
		} else {
			if (inventory.contains(hide.ind_id)) {
				return state.WALK_TO;
			} else {
				return state.WALK_FROM;
			}
		}

	}

	@Override
	public int loop() {
		switch (getState()) {
			case TURN_ON_RUN:
				walking.setRun(true);
				for (int i = 0; i < 10 && !walking.isRunEnabled(); i++) {
					Methods.sleep(100, 200);
				}
				Methods.sleep(Methods.random(1200, 1500));
				break;
			case REST:
				walking.rest();
				Methods.sleep(Methods.random(1200, 1500));
				break;
			case WALK_TO:
				current_path.traverse();
				break;
			case WALK_FROM:
				path_back.traverse();
				break;
			case TRADE_TANNER:
				final RSNPC tanner_npc = npcs.getNearest(AutoTanner.TANNER);
				if (tanner_npc != null && getMyPlayer().getAnimation() == -1) {
					tanner_npc.doAction("Trade");
					Methods.sleep(Methods.random(1200, 1500));
				}
				break;
			case BANK:
				if (!bank.isOpen()) {
					if (getMyPlayer().getAnimation() != -1) {
						Methods.sleep(Methods.random(1200, 1500));
					} else {
						bank.open();
					}
				} else {

					if (!inventory.contains(hide.ind_id)) {
						if (inventory.contains(hide.tanned_id)) {
							bank.deposit(hide.tanned_id, 0);
							Methods.sleep(Methods.random(1200, 1500));
						}
						if (bank.getItem(hide.ind_id) != null) {
							bank.withdraw(hide.ind_id, 0);
							Methods.sleep(Methods.random(1200, 1500));
						} else {
							log("Out of Hides.");
							break;
						}
					}
				}
				break;
			case INTERFACE:
				final RSInterface tan_inter = interfaces.get(324);
				if (tan_inter.isValid()) {
					tan_inter.getComponent(hide.component_id).doAction("Tan All");
					for (int i = 0; i < 100 && inventory.contains(hide.tanned_id); i++) {
						Methods.sleep(20);
					}
					made = made + inventory.getCount(hide.tanned_id);
					profitMade = profit * made;

				}
				break;
		}
		return Methods.random(800, 1200);
	}

	@Override
	public void messageReceived(final MessageEvent e) {

	}

	@Override
	public void onRepaint(final Graphics g1) {
		final Graphics2D g = (Graphics2D) g1;
		final long millis = System.currentTimeMillis() - startTime;
		final String time = Timer.format(millis);
		final int profitHour = (int) (3600 * profitMade / millis);
		g.setColor(AutoTanner.COLOR_1);
		g.fillRect(14, 350, 474, 99);
		g.setColor(AutoTanner.COLOR_2);
		g.setStroke(AutoTanner.STROKE);
		g.drawRect(14, 350, 474, 99);
		g.setFont(AutoTanner.FONT_1);
		g.setColor(AutoTanner.COLOR_3);
		g.drawString("AutoTanner", 209, 374);
		g.setFont(AutoTanner.FONT_2);
		g.drawString("Profit: " + profitMade, 18, 390);
		g.drawString("Profit Hour: " + profitHour, 18, 400);
		g.drawString("Time Ran: " + time, 182, 390);
		g.drawString("Status: " + getState().toString(), 182, 400);

	}

	@Override
	public boolean onStart() {
		new Gui().setVisible(true);
		while (gui_on) {
			Methods.sleep(20);
		}
		current_path = walking.newTilePath(AutoTanner.OLD_PATH);
		path_back = walking.newTilePath(AutoTanner.OLD_PATH).reverse();
		profit = grandExchange.lookup(hide.tanned_id).getGuidePrice()
				- grandExchange.lookup(hide.ind_id).getGuidePrice();
		startTime = System.currentTimeMillis();
		return game.isLoggedIn();
	}

}