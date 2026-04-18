package dev.dunkleente.mctiers;

import dev.dunkleente.utility.ColorUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * PlayerTier
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */

@Getter
@RequiredArgsConstructor
public enum PlayerTier {
    LT5(1, "<#D3D3D3>LT5"),
    HT5(2, "<#808080>HT5"),
    LT4(3, "<#90EE90>LT4"),
    HT4(4, "<#006400>HT4"),
    LT3(5, "<#EEE8AA>LT3"),
    HT3(6, "<#DAA520>HT3"),
    LT2(7, "<#FFE4B5>LT2"),
    HT2(8, "<#FFA500>HT2"),
    LT1(9, "<#FFB6C1>LT1"),
    HT1(10, "<#FF0000>HT1"),
    UNRANKED(-1, "<#D3D3D3>N/A");

    private final int tierValue;
    private final @NotNull String formatted;

    @NotNull
    public String asString() {
        return this.formatted;
    }

    @NotNull
    public static PlayerTier from(final int value) {
        for (final PlayerTier tier : values()) {
            if (tier.tierValue == value) return tier;
        }
        return UNRANKED;
    }

    public static int wrap(final int tier, final int pos) {
        return 12 - (tier * 2) - pos;
    }
}