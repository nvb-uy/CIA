package elocindev.customitemattributes;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elocindev.customitemattributes.builder.AttributeBuilderCallback;
import elocindev.customitemattributes.config.CIAConfig;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

public class CustomItemAttributes implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("customitemattributes");

	public static final String MOD_ID = "customitemattributes";
	public static CIAConfig CONFIG;

	@Override
	public void onInitialize() {
		NecConfigAPI.registerConfig(CIAConfig.class);
		CONFIG = CIAConfig.INSTANCE;

		AttributeBuilderCallback.register();
	}
}