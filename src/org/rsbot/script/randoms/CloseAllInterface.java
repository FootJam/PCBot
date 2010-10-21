package org.rsbot.script.randoms;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;

import java.util.LinkedList;
import java.util.List;

/**
 * Closes interfaces that scripts may open by mistake.
 * <p/>
 * Updated 23/09/10 by Aribter.
 */
@ScriptManifest(authors = {"Jacmob", "HeyyamaN"}, name = "InterfaceCloser", version = 1.7)
public class CloseAllInterface extends Random {
	
	static class ComponentDef {
		
		int parent;
		int child;
		
		public ComponentDef(int parent, int child) {
			this.parent = parent;
			this.child = child;
		}
		
	}

    private List<ComponentDef> components = new LinkedList<ComponentDef>();

    {
        addChild(743, 20); // Audio
        addChild(767, 10); // Bank help
        addChild(499, 29); // Stats
        addChild(594, 48); // Report
        addChild(275, 8); // Quest
        addChild(206, 16); // Price check
        addChild(266, 11); // Grove
        addChild(102, 13); // Death items
        addChild(14, 3); // Pin settings
        addChild(157, 13); // Quick chat help
        addChild(764, 2); // Objectives
        addChild(895, 19); // Advisor
		addChild(109, 13); // Grand exchange collection
    }

    private void addChild(int parent, int idx) {
        components.add(new ComponentDef(parent, idx));
    }

    public boolean activateCondition() {
        if (game.isLoggedIn()) {
            if (interfaces.get(755).getComponent(44).isValid()) { // World map
                if (interfaces.getComponent(755, 0).getComponents().length > 0) {
                    return true;
                }
            }
            for (ComponentDef c : components) {
                if (interfaces.getComponent(c.parent, c.child).isValid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int loop() {
        sleep(random(500, 900));

        if (interfaces.get(755).isValid() && (interfaces.getComponent(755, 0).getComponents().length > 0)) {
            interfaces.getComponent(755, 44).doClick();
            return random(500, 900);
        }
        for (ComponentDef c : components) {
            if (interfaces.getComponent(c.parent, c.child).isValid()) {
            	interfaces.getComponent(c.parent, c.child).doClick();
                sleep(random(500, 900));
                break;
            }
        }

        return -1;
    }

}