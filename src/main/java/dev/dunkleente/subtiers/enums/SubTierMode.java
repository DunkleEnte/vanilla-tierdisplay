package dev.dunkleente.subtiers.enums;

import dev.dunkleente.mctiers.enums.GameMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SubTierMode
 *
 * @author DunkleEnte
 * @since 18.04.2026
 */

@Getter
@RequiredArgsConstructor
public enum SubTierMode {
    MINECART("minecart", "Minecart", "<#FF0000>☠"),
    DIA_CRYSTAL("dia_crystal", "Dia Crystal", "<#68D4FF>♦"),
    DEBUFF("debuff", "DeBuff", "<#FFEE00>\uD83D\uDEE1"),
    ELYTRA("elytra", "Elytra", "<#ACA3FF>☁"),
    SPEED("speed", "Speed", "<#00C1FF>⚗"),
    CREEPER("creeper", "Creeper", "<#8DFB08>☄"),
    MANHUNT("manhunt", "Manhunt", "<#7E7E7E>⚔"),
    DIA_SMP("dia_smp", "Dia SMP", "<#68D4FF>⛨"),
    BOW("bow", "Bow", "<#C68946>\uD83C\uDFF9"),
    BED("bed", "Bed", "<#FF0000>⌂"),
    OG_VANILLA("og_vanilla", "OG Vanilla" ,"<#FFD900>\uD83C\uDF56"),
    TRIDENT("trident", "Trident", "<#00ACFF>\uD83D\uDD31");


    private final @NotNull String apiKey;
    private final @NotNull String name;
    private final @NotNull String icon;

    @Nullable
    public static SubTierMode fromString(final @NotNull String input) {
        for (final SubTierMode mode : values()) {
            if (mode.apiKey.equalsIgnoreCase(input) || mode.name.equalsIgnoreCase(input)) {
                return mode;
            }
        }
        return null;
    }
}
