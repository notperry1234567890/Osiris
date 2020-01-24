package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.modules.combat.Surround;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.friends.Friends;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;

public class ESP2 extends Module {
    public ESP2() {
        super("CsgoESP", Category.RENDER);
    }

    Setting players;
    Setting passive;
    Setting monsters;
    Setting items;
    Setting xpBottles;
    Setting crystals;

    public void setup(){
        OsirisMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, true));
        OsirisMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(monsters = new Setting("Monsters", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false));
        OsirisMod.getInstance().settingsManager.rSetting(xpBottles = new Setting("XpBottles", this, false));
    }

    public void onWorldRender(RenderEvent event) {
        if (mc.getRenderManager().options == null) return;
                boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;
                float viewerYaw = mc.getRenderManager().playerViewY;

                mc.world.loadedEntityList.stream()
                        .filter(entity -> mc.player != entity)
                        .forEach(e -> {
                            OsirisTessellator.prepareGL();
                            GlStateManager.pushMatrix();
                            Vec3d pos = Surround.getInterpolatedPos(e, mc.getRenderPartialTicks());
                            GlStateManager.translate(pos.x-mc.getRenderManager().renderPosX, pos.y-mc.getRenderManager().renderPosY, pos.z-mc.getRenderManager().renderPosZ);
                            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1), 1.0F, 0.0F, 0.0F);
                            //GlStateManager.disableLighting();
                            //GlStateManager.depthMask(false);

                            //GlStateManager.disableDepth();

                            //GlStateManager.enableBlend();
                            //GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                            glColor4f(1, 1, 1, 0.5f);

                            //GlStateManager.disableTexture2D();
                            glLineWidth(3f);
                            glEnable(GL_LINE_SMOOTH);

                            if(e instanceof EntityPlayer && players.getValBoolean()) {
                                if (Friends.isFriend(e.getName())) {
                                    glColor4f(0, 1, 1, 0.5f);
                                } else {
                                    glColor4f(1f, 0f, 0f, 0.5f);
                                }
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            glColor4f(1, 1, 1, 0.5f);

                            if(ESP.isPassive(e) && passive.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(ESP.isMonster(e) && monsters.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(e instanceof EntityItem && items.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(e instanceof EntityExpBottle && xpBottles.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }


                            if(e instanceof EntityEnderCrystal && crystals.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(-e.width / 2, e.height / 3);
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(((-e.width / 3) * 2) / 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(((-e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(-e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(((e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(((e.width / 3) * 2) / 2, 0);
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(e.width / 2, e.height / 3);
                                }
                                glEnd();
                            }

                            OsirisTessellator.releaseGL();
                            GlStateManager.popMatrix();
                        });
                //GlStateManager.enableDepth();
                //GlStateManager.depthMask(true);
                //GlStateManager.disableTexture2D();
                //GlStateManager.enableBlend();
                //GlStateManager.disableAlpha();
                //GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                //GlStateManager.shadeModel(GL11.GL_SMOOTH);
                //GlStateManager.disableDepth();
                //GlStateManager.enableCull();
                //GlStateManager.glLineWidth(1);
                glColor4f(1,1,1, 1);
    }
}