package dev.dunkleente.mctiers.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * GameMode
 *
 * @author DunkleEnte
 * @since 17.04.2026
 */
@Getter
@RequiredArgsConstructor
public enum GameMode {
    VANILLA("vanilla", "Vanilla", "<#FF7CCC>♦"),
    UHC("uhc", "UHC", "<#FFEE00>⛨"),
    POT("pot", "Pot", "<#FF0000>\uD83E\uDDEA"),
    NETHOP("nethop", "NethOP", "<#C285FF>\uD83C\uDFF9"),
    SMP("smp", "SMP", "<#FF7200>⚑"),
    SWORD("sword", "Sword", "<#00C1FF>\uD83D\uDDE1"),
    AXE("axe", "Axe", "<#8DDAFF>\uD83E\uDE93"),
    MACE("mace", "Mace", "<#BFADFF>\uD83D\uDEE1");

    private final @NotNull String apiKey;
    private final @NotNull String displayName;
    private final @NotNull String icon;

    @NotNull
    public String asString() {
        return this.icon;
    }
    @Nullable
    public static GameMode fromString(final @NotNull String input) {
        for (final GameMode mode : values()) {
            if (mode.apiKey.equalsIgnoreCase(input) || mode.displayName.equalsIgnoreCase(input)) {
                return mode;
            }
        }
        return null;
    }
}