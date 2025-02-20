package elocindev.customitemattributes.config;

import java.nio.file.Path;
import java.util.List;

import elocindev.customitemattributes.api.GenericAttribute;
import elocindev.customitemattributes.api.ItemProperty;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.Comment;
import elocindev.necronomicon.config.NecConfig;

public class CIAConfig {
    public static final String FOLDER = "custom_item_attributes";
    public static final String FILE_NAME = "overrides.json5";
    public static final int CURRENT_CONFIG_VERSION = 2;

    @NecConfig
    public static CIAConfig INSTANCE;

    public static String getFile() {
        Path folder = Path.of(NecConfigAPI.getFile(FOLDER));

        if (!folder.toFile().exists())
            folder.toFile().mkdirs();
          
        return folder.toString()+"/"+FILE_NAME;
    }

    @Comment("---------------------------------------------------------------------------------------------------------------------------------")
    @Comment("                                           Custom Item Attributes (CIA) by ElocinDev")
    @Comment("---------------------------------------------------------------------------------------------------------------------------------")
    @Comment("                                                       Overrides Config ")
    @Comment("                                                Reloaded by restarting the game")
    @Comment("---------------------------------------------------------------------------------------------------------------------------------")
    @Comment("Option Index:")
    @Comment("      item        :           The item's identifier. You can get this using the autocomplete of the /give command.")
    @Comment("      slot        :           A list of slots where the attributes should be applied.")
    @Comment("      attribute   :           The Identifier of the attribute. You can get it using the autocomplete of the /attribute command.")
    @Comment("      value       :           The value of the desired attribute. You can use negatives too, must be a decimal number.")
    @Comment("      id          :           The identifier of the modifier. Can be any valid Identifier.")
    @Comment("      operation   :           The operation to calculate the attribute. One of add_value, add_multiplied_base, or add_multiplied_total.")
    @Comment("      unbreakable :           Whether or not the item should be unbreakable.")
    @Comment("      force_unbreakable :     Force the unbreakable tag over other mods. Setting this to true will break the Hardening Catalyst from Things from being used on that item!")
    @Comment("---------------------------------------------------------------------------------------------------------------------------------")
    @Comment("Slots:")
    @Comment("      any         :   Applies to any slot.")
    @Comment("      mainhand    :   The main hand slot.")
    @Comment("      offhand     :   The off hand slot.")
    @Comment("      hand        :   Applies to either hand slot.")
    @Comment("      head        :   The head slot.")
    @Comment("      chest       :   The chest slot.")
    @Comment("      legs        :   The legs slot.")
    @Comment("      feet        :   The feet slot.")
    @Comment("      armor       :   Applies to the head/chest/legs/feet slots.")
    @Comment("      body        :   Used for Wolf/Horse armor")
    @Comment("------------------------------------------------------------------------------------------------------------------------------")
    @Comment("The example showcases a config to add +10 attack damage and +20% attack speed to an \"example_item\", on both the main hand and off hand.")
    @Comment("Each of the attributes are overrides for modifiers of the same attribute type, depending on each slot correspondingly.")

    public List<ItemProperty> items = List.of(
        new ItemProperty(
            "examplemod:example_item",
            List.of(
                "hand"
            ),
            List.of(
                new GenericAttribute<>("minecraft:generic.attack_damage", 10.0, "add_value", "examplemod:attack_damage_modifier_id"),
                new GenericAttribute<>("minecraft:generic.attack_speed", 0.20, "add_multiplied_base", "examplemod:attack_speed_modifier_id")
            )
        )
    );

    @Comment("Don't touch this!")
    public int CONFIG_VERSION = 3;
}
