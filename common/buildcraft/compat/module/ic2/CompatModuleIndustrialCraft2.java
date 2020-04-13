/*
 * Copyright (c) 2020 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.compat.module.ic2;

import net.minecraft.item.Item;

import buildcraft.compat.CompatModuleBase;

import buildcraft.lib.misc.StackMatchingPredicate;
import buildcraft.lib.misc.StackNbtMatcher;
import buildcraft.lib.misc.StackUtil;

public class CompatModuleIndustrialCraft2 extends CompatModuleBase {
    @Override
    public String compatModId() {
        return "ic2";
    }

    @Override
    public void preInit() {
        registerCableMatchingPredicate();
    }

    private void registerCableMatchingPredicate() {
        // Distinguish cables by type and insulation
        // https://github.com/BuildCraft/BuildCraft/issues/4553
        Item cable = Item.getByNameOrId("ic2:cable");
        if (cable != null) {
            StackMatchingPredicate predicate = new StackNbtMatcher("insulation", "type");
            StackUtil.registerMatchingPredicate(cable, predicate);
        }
    }
}
