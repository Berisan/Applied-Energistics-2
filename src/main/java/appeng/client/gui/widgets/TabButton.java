/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TabButton extends ButtonWidget implements ITooltip {
    public static final Identifier TEXTURE_STATES = new Identifier("appliedenergistics2", "textures/guis/states.png");
    private final ItemRenderer itemRenderer;
    private boolean hideEdge;
    private int myIcon = -1;
    private ItemStack myItem;

    public TabButton(final int x, final int y, final int ico, final Text message, final ItemRenderer ir,
            PressAction onPress) {
        super(x, y, 22, 22, message, onPress);

        this.myIcon = ico;
        this.itemRenderer = ir;
    }

    /**
     * Using itemstack as an icon
     *
     * @param x       x pos of button
     * @param y       y pos of button
     * @param ico     used icon
     * @param message mouse over message
     * @param ir      renderer
     */
    public TabButton(final int x, final int y, final ItemStack ico, final Text message, final ItemRenderer ir,
            PressAction onPress) {
        super(x, y, 22, 22, message, onPress);
        this.myItem = ico;
        this.itemRenderer = ir;
    }

    @Override
    public void renderButton(MatrixStack matrices, final int x, final int y, float partial) {
        final MinecraftClient minecraft = MinecraftClient.getInstance();

        if (this.visible) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            minecraft.getTextureManager().bindTexture(TEXTURE_STATES);

            RenderSystem.enableAlphaTest();

            // Selects the button border from the sprite-sheet, where each type occupies a
            // 2x2 slot
            int uv_x = 8 + (this.hideEdge ? 0 : 2);
            int uv_y = 12 + (this.isFocused() ? 2 : 0);

            final int offsetX = this.hideEdge ? 1 : 0;

            drawTexture(matrices, this.x, this.y, uv_x * 16, uv_y * 16, 25, 22);

            if (this.myIcon >= 0) {
                uv_y = this.myIcon / 16;
                uv_x = this.myIcon - uv_y * 16;

                drawTexture(matrices, offsetX + this.x + 3, this.y + 3, uv_x * 16, uv_y * 16, 16, 16);
            }

            RenderSystem.disableAlphaTest();

            if (this.myItem != null) {
                this.itemRenderer.zOffset = 100.0F;
                this.itemRenderer.renderInGuiWithOverrides(this.myItem, offsetX + this.x + 3, this.y + 3);
                this.itemRenderer.zOffset = 0.0F;
            }
        }
    }

    @Override
    public Text getTooltipMessage() {
        return getMessage();
    }

    @Override
    public int getTooltipAreaX() {
        return this.x;
    }

    @Override
    public int getTooltipAreaY() {
        return this.y;
    }

    @Override
    public int getTooltipAreaWidth() {
        return 22;
    }

    @Override
    public int getTooltipAreaHeight() {
        return 22;
    }

    @Override
    public boolean isTooltipAreaVisible() {
        return this.visible;
    }

    public boolean getHideEdge() {
        return this.hideEdge;
    }

    public void setHideEdge(final boolean hideEdge) {
        this.hideEdge = hideEdge;
    }
}
