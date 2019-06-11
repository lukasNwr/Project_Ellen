package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor{

        private Teleport destinationTeleport;
        private boolean used;
        private Player player;


        public Teleport(Teleport destinationTeleport){
            Animation teleporter;
            teleporter = new Animation("sprites/lift.png", 48,48);
            setAnimation(teleporter);
            if (destinationTeleport != this && destinationTeleport != null){
                this.destinationTeleport = destinationTeleport;

            }

        }

        public void setDestination(Teleport destinationTeleport){
            if(this.destinationTeleport == destinationTeleport){
                this.destinationTeleport = null;
            }
            this.destinationTeleport = destinationTeleport;
        }

        public void teleportPlayer() {
            if(this.intersects(player) && destinationTeleport != null && !used) {
                player.setPosition(destinationTeleport.getPosX(), destinationTeleport.getPosY());
                destinationTeleport.used = true;
            }
            if(!this.intersects(player) && this.used){
                this.used = false;
            }
        }


}
