package elocindev.customitemattributes.config;


import java.util.ArrayList;
import java.util.List;

import elocindev.customitemattributes.api.GenericAttribute;
import elocindev.customitemattributes.api.ItemProperty;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.Comment;
import elocindev.necronomicon.config.NecConfig;

public class CIAConfig {
    @NecConfig
    public static CIAConfig INSTANCE;

    public static String getFile() {
        return NecConfigAPI.getFile("custom_item_attributes.json5");
    }
    @Comment("------------------------------------------------------------------------------------------------------------------------------")
    @Comment("                                        Custom Item Attributes (CIA) by ElocinDev")
    @Comment("------------------------------------------------------------------------------------------------------------------------------")
    @Comment("                                                         Config ")
    @Comment("                            Reloaded via datapack reload (/reload) or restarting the game")
    @Comment("------------------------------------------------------------------------------------------------------------------------------")
    @Comment("Index:")
    @Comment("      item        :   The item's identifier. You can get this using the autocomplete of the /give command.")
    @Comment("      slot        :   Each override option represents a slot where the attributes get applied to. Hands or armors.")
    @Comment("      attribute   :   The Identifier of the attribute. You can get it using the autocomplete of the /attribute command.")
    @Comment("      value       :   The value of the desired attribute. You can use negatives too, must be a decimal number.")
    @Comment("      operation   :   The operation to calculate the attribute. Addition or Multiply.")
    @Comment("      unbreakable :   Whether or not the item should be unbreakable.")
    @Comment("------------------------------------------------------------------------------------------------------------------------------")
    @Comment("The example showcases a config to add +10 attack damage and +20% attack speed to an \"example_item\", only on the main hand.")
    @Comment("Each of the attributes are overrides for modifiers of the same attribute type, depending on each slot correspondingly.")

    public List<ItemProperty> items = List.of(
        new ItemProperty("examplemod:example_item",

            List.of(
                new GenericAttribute<>("minecraft:generic.attack_damage", 10.0, "ADDITION"),
                new GenericAttribute<>("minecraft:generic.attack_speed", 0.20, "MULTIPLY_BASE")
            ),

            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),

            false
        )
    );
}
