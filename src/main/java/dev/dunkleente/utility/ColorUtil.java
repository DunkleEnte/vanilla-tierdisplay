package dev.dunkleente.utility;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * ColorUtil
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
@UtilityClass
public class ColorUtil {

    public Component parse(String input){
        return MiniMessage.miniMessage().deserialize(input).decoration(TextDecoration.ITALIC, false);
    }

    public Component empty(){
        return Component.empty();
    }
}
