package sk.tuke.kpi.oop.game;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Prototype.Money;
import sk.tuke.kpi.oop.game.Prototype.MoneyFactory;
import sk.tuke.kpi.oop.game.Prototype.MoneyType;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.CollectorController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.scenarios.Custom;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen-Ripley", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup);

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
        Scene scene = new World("world", "maps/custom.tmx", new Custom.Factory());


        // pridanie sceny do hry
        game.addScene(scene);

        scene.addListener(new Custom() {
            @Override
            public void sceneInitialized(@NotNull Scene scene) {
                super.sceneInitialized(scene);
            }
        });

        // spustenie hry
        game.start();

        scene.getInput().onKeyPressed(key -> {
            if (key == Input.Key.ESCAPE) {
                game.stop();
            }
        });

    }
}
