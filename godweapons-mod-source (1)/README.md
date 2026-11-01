# God Weapons (Fabric, Minecraft 1.21.1)

Three absurdly overpowered, unbreakable weapons:

| Item | Type | Abilities |
|---|---|---|
| **Void Reaper** | Sword | 25% chance per hit to open a black hole that pulls enemies in, then collapses for 8 hearts of damage to everything caught. 5 s cooldown. |
| **Godslayer** | Axe | +100% damage, half of the bonus ignores armor. Every 5th hit = massive lightning, damaging all enemies within 6 blocks. |
| **Celestial Judgement** | Sword | Every kill calls a meteor (15 hearts, launches nearby enemies). Every 10 kills = meteor shower. |

Find them in the Combat creative tab, or craft them:

- Void Reaper: `ENE / ENE / _T_` (E = Eye of Ender, N = Netherite Ingot, T = Nether Star)
- Godslayer: `NN / NT / _R` (N = Netherite Ingot, T = Nether Star, R = Blaze Rod)
- Celestial Judgement: `TNT / _N_ / _R_`

Special abilities only hit hostile mobs (never players, pets or villagers).
To hit every mob, set `HIT_ALL_MOBS = true` in `effect/Util.java`.

## Build with GitHub (no install needed)
1. Create a new GitHub repo and upload everything in this folder (keep the folder structure, including `.github`).
2. Open the **Actions** tab -> the "Build Mod" workflow runs automatically.
3. When it's green, open the run and download the **GodWeapons-mod** artifact.
4. Unzip it and use `godweapons-1.0.0.jar` (not the `-sources` one).

## Install
Minecraft 1.21.1 + Fabric Loader + Fabric API + Java 21. Drop the jar in `mods/`.
