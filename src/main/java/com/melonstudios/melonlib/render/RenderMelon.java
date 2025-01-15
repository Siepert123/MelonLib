package com.melonstudios.melonlib.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Helper class with rendering utils.
 * <p>
 * Named after the RenderDragon.
 * @since 1.0
 */
@SideOnly(Side.CLIENT)
public class RenderMelon {
    private RenderMelon() {}

    /**
     * Invokes the {@link GL11#glScalef(float, float, float)} method, but uses the pixel measurement instead of block measurement.
     * @param x X-scale in pixels
     * @param y Y-scale in pixels
     * @param z Z-scale in pixels
     * @since 1.0
     */
    public static void glScalePixels(float x, float y, float z) {
        GL11.glScalef(x / 16, y / 16, z / 16);
    }

    /**
     * Invokes the {@link GL11#glTranslatef(float, float, float)} method, but uses pixel measurement instead of block measurement.
     * @param x Translation along the X-axis in pixels
     * @param y Translation along the Y-axis in pixels
     * @param z Translation along the Z-axis in pixels
     * @since 1.0
     */
    public static void glTranslatePixels(float x, float y, float z) {
        GL11.glTranslatef(x / 16, y / 16, z / 16);
    }

    /**
     * Rotates the matrix by a certain angle along a certain axis.
     * @param angle How many degrees should be rotated
     * @param axis The axis to rotate along
     * @since 1.0
     */
    public static void glRotateAround(float angle, EnumFacing.Axis axis) {
        if (angle == 0) return;
        GL11.glRotatef(angle, axis == EnumFacing.Axis.X ? 1 : 0, axis == EnumFacing.Axis.Y ? 1 : 0, axis == EnumFacing.Axis.Z ? 1 : 0);
    }

    /**
     * Renders a piece of text in the world.
     * @param fontRenderer The font renderer
     * @param text The text to display
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     * @param verticalShift The vertical shift in pixels
     * @param yaw The yaw rotation of the text
     * @param pitch The pitch rotation of the text
     * @param occlude Whether to hide the text behind walls
     * @param disableLight Whether to disable the lighting
     * @param color The color of the text (in ARGB format; -1 is white)
     * @since 1.0
     */
    public static void renderText(FontRenderer fontRenderer, String text, float x, float y, float z,
                                  int verticalShift, float yaw, float pitch, boolean occlude, boolean disableLight, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0, 1, 0);
        GlStateManager.rotate(yaw, 0, 1, 0);
        GlStateManager.rotate(pitch, 1, 0, 0);
        GlStateManager.scale(-0.025, -0.025, 0.025);
        if (disableLight) GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        if (!occlude) GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        if (!occlude) {
            fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, verticalShift, 553648127);
            GlStateManager.enableDepth();
        }

        GlStateManager.depthMask(true);
        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, verticalShift, color);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a piece of text in the world.
     * @param fontRenderer The font renderer
     * @param text The text to display
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     * @param verticalShift The vertical shift in pixels
     * @param yaw The yaw rotation of the text
     * @param pitch The pitch rotation of the text
     * @param occlude Whether to hide the text behind walls
     * @param disableLight Whether to disable the lighting
     * @since 1.0
     */
    public static void renderText(FontRenderer fontRenderer, String text, float x, float y, float z,
                                  int verticalShift, float yaw, float pitch, boolean occlude, boolean disableLight) {
        renderText(fontRenderer, text, x, y, z, verticalShift, yaw, pitch, occlude, disableLight, -1);
    }

    /**
     * Renders a piece of text in the world, with a transparent backdrop.
     * @param fontRenderer The font renderer
     * @param text The text to display
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     * @param verticalShift The vertical shift in pixels
     * @param yaw The yaw rotation of the text
     * @param pitch The pitch rotation of the text
     * @param occlude Whether to hide the text behind walls
     * @param disableLight Whether to disable the lighting
     * @param color The color of the text (in ARGB format; -1 is white)
     * @since 1.0
     */
    public static void renderTextWithBackdrop(FontRenderer fontRenderer, String text, float x, float y, float z,
                                              int verticalShift, float yaw, float pitch, boolean occlude, boolean disableLight, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.glNormal3f(0, 1, 0);
        GlStateManager.rotate(yaw, 0, 1, 0);
        GlStateManager.rotate(pitch, 1, 0, 0);
        GlStateManager.scale(-0.025, -0.025, 0.025);
        if (disableLight) GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        if (!occlude) GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        int width = fontRenderer.getStringWidth(text) / 2;
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(-width - 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        buffer.pos(-width - 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        buffer.pos(width + 1, 8 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        buffer.pos(width + 1, -1 + verticalShift, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();

        if (!occlude) {
            fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, verticalShift, 553648127);
            GlStateManager.enableDepth();
        }

        GlStateManager.depthMask(true);
        fontRenderer.drawString(text, -fontRenderer.getStringWidth(text) / 2, verticalShift, color);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    /**
     * Renders a piece of text in the world, with a transparent backdrop.
     * @param fontRenderer The font renderer
     * @param text The text to display
     * @param x The X-coordinate
     * @param y The Y-coordinate
     * @param z The Z-coordinate
     * @param verticalShift The vertical shift in pixels
     * @param yaw The yaw rotation of the text
     * @param pitch The pitch rotation of the text
     * @param occlude Whether to hide the text behind walls
     * @param disableLight Whether to disable the lighting
     * @since 1.0
     */
    public static void renderTextWithBackdrop(FontRenderer fontRenderer, String text, float x, float y, float z,
                                              int verticalShift, float yaw, float pitch, boolean occlude, boolean disableLight) {
        renderTextWithBackdrop(fontRenderer, text, x, y, z, verticalShift, yaw, pitch, occlude, disableLight, -1);
    }

    /**
     * Checks whether the F3 screen is opened.
     * @return True if the F3 screen is opened
     * @since 1.0
     */
    public static boolean isF3ScreenEnabled() {
        return Minecraft.getMinecraft().gameSettings.showDebugInfo;
    }
}
