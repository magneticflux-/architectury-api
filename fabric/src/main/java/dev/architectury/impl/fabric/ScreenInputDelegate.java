/*
 * This file is part of architectury.
 * Copyright (C) 2020, 2021 architectury
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package dev.architectury.impl.fabric;

import dev.architectury.event.events.client.ClientScreenInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;

public interface ScreenInputDelegate {
    Screen architectury_delegateInputs();
    
    class DelegateScreen extends Screen {
        private Screen parent;
        
        public DelegateScreen(Screen parent) {
            super(TextComponent.EMPTY);
            this.parent = parent;
        }
        
        @Override
        public boolean charTyped(char c, int i) {
            if (ClientScreenInputEvent.CHAR_TYPED_PRE.invoker().charTyped(Minecraft.getInstance(), parent, c, i).isPresent())
                return true;
            if (parent.charTyped(c, i))
                return true;
            return ClientScreenInputEvent.CHAR_TYPED_POST.invoker().charTyped(Minecraft.getInstance(), parent, c, i).isPresent();
        }
    }
}
