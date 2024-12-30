package teayettle.lavabowls;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EdibleLavaBowls implements ModInitializer {
	public static final String MODID = "edible-lava-bowls";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		Register.registerItems();
	}
}