package org.rsbot.script.background;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.rsbot.script.BackgroundScript;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.internal.wrappers.TileFlags;
import org.rsbot.script.methods.Web;
import org.rsbot.script.wrappers.RSTile;
import org.rsbot.service.WebQueue;
import org.rsbot.util.GlobalConfiguration;

@ScriptManifest(name = "Web Data Loader", authors = {"Timer"})
public class WebLoader extends BackgroundScript {
	@Override
	public boolean activateCondition() {
		return !Web.loaded;
	}

	@Override
	public int loop() {
		if (!Web.loaded) {
			try {
				int badRemoved = 0;
				int redundantRemoved = 0;
				final BufferedReader br = new BufferedReader(new FileReader(GlobalConfiguration.Paths.getWebCache()));
				String line;
				final HashMap<RSTile, TileFlags> theFlagsList = new HashMap<RSTile, TileFlags>();
				while ((line = br.readLine()) != null) {
					final String[] data = line.split("tile=data");
					if (data.length == 2) {
						final String[] tileData = data[0].split(",");
						final String[] abbData = data[1].split("=");
						if (tileData.length == 3) {
							try {
								final RSTile tile = new RSTile(Integer.parseInt(tileData[0]), Integer.parseInt(tileData[1]), Integer.parseInt(tileData[2]));
								final TileFlags tileFlags = new TileFlags(tile, null);
								for (final String abb : abbData) {
									if (abb.length() > 0) {
										try {
											tileFlags.addKey(Integer.parseInt(abb));
										} catch (final Exception e) {
										}
									}
								}
								if (tileFlags.containsKey(0)) {
									WebQueue.Remove(line);//Line is redundant as of Thursday, May 5, 2011.
									redundantRemoved++;
								} else {
									if (theFlagsList.containsKey(tile)) {
										WebQueue.Remove(line);//Line is double, remove from file--bad collection.
										badRemoved++;
									} else {
										theFlagsList.put(tile, tileFlags);
									}
								}
							} catch (final Exception e) {
							}
						} else {
							WebQueue.Remove(line);//Line is bad, remove from file.
							badRemoved++;
						}
					} else {
						WebQueue.Remove(line);//Line is bad, remove from file.
						badRemoved++;
					}
				}
				Web.map.putAll(theFlagsList);
				Web.loaded = true;
			} catch (final Exception e) {
				log("Failed to load the web.. trying again.");
			}
		}
		if (Web.loaded) {
			deactivate(getID());
		}
		return -1;
	}

	@Override
	public int iterationSleep() {
		return 5000;
	}
}