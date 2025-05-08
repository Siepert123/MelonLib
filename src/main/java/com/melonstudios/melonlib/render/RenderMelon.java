package com.melonstudios.melonlib.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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
     * Invokes the {@link GL11#glScaled(double, double, double) GL11.glScaled()} method, but uses the pixel measurement instead of block measurement.
     * @param x X-scale in pixels
     * @param y Y-scale in pixels
     * @param z Z-scale in pixels
     * @since 1.0
     */
    public static void glScalePixels(double x, double y, double z) {
        GL11.glScaled(x / 16, y / 16, z / 16);
    }

    /**
     * Invokes the {@link GL11#glTranslated(double, double, double) GL11.glScaled()} method, but uses pixel measurement instead of block measurement.
     * @param x Translation along the X-axis in pixels
     * @param y Translation along the Y-axis in pixels
     * @param z Translation along the Z-axis in pixels
     * @since 1.0
     */
    public static void glTranslatePixels(double x, double y, double z) {
        GL11.glTranslated(x / 16, y / 16, z / 16);
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
     * Renders a blockstate that is resized.
     *
     * @param state The blockstate to render
     * @param brightness The brightness
     * @param pos The position to render it at
     * @param minX Min X corner
     * @param minY Min Y corner
     * @param minZ Min Z corner
     * @param maxX Max X corner
     * @param maxY Max Y corner
     * @param maxZ Max Z corner
     * @since 1.0
     */
    public static void renderResizedBlockstate(IBlockState state, float brightness, BlockPos pos, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        GL11.glPushMatrix();
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderResizedBlockstateUnsafe(state, brightness, pos,
                Math.min(minX, maxX), Math.min(minY, maxY),
                Math.min(minZ, maxZ), Math.max(minX, maxX), Math.max(minY, maxY), Math.max(minZ, maxZ)
        );
        GL11.glPopMatrix();
    }

    /**
     * @see RenderMelon#renderResizedBlockstate(IBlockState, float, BlockPos, double, double, double, double, double, double) The recommended usage
     */
    public static void renderResizedBlockstateUnsafe(IBlockState state, float brightness, BlockPos pos, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        GL11.glTranslatef(pos.getX(), pos.getY(), pos.getZ());
        glTranslatePixels(minX, minY, minZ);
        double dx = maxX - minX;
        double dy = maxY - minY;
        double dz = maxZ - minZ;
        glScalePixels(dx, dy, dz);
        IBakedModel model = Minecraft.getMinecraft()
                        .getBlockRendererDispatcher().getModelForState(state);
        GL11.glRotatef(-90, 0, 1, 0);
        Minecraft.getMinecraft().getBlockRendererDispatcher()
                        .getBlockModelRenderer().renderModelBrightness(model, state, brightness, true);
    }

    /**
     * Checks whether the F3 screen is opened.
     * @return True if the F3 screen is opened
     * @since 1.0
     */
    public static boolean isF3ScreenEnabled() {
        return Minecraft.getMinecraft().gameSettings.showDebugInfo;
    }

    /**
     * @return The default font renderer
     * @since 1.0
     */
    public static FontRenderer getDefaultFontRenderer() {
        return Minecraft.getMinecraft().fontRenderer;
    }

    /**
     * @return The standard galactic alphabet font renderer
     * @see RenderMelon#getDefaultFontRenderer() The default font renderer
     * @since 1.0
     */
    public static FontRenderer getEnchantmentFontRenderer() {
        return Minecraft.getMinecraft().standardGalacticFontRenderer;
    }

    /**
     * @return The REAL Minecraft
     */
    public static Minecraft getTheRealMinecraftInstanceYesForRealIAmNotJokingGetItHereNowForOneHundredPercentOffOkayThisIsNotATroll() {
        return Minecraft.getMinecraft();
    }
}
