module passwordGame {
    requires javafx.controls;
    requires javafx.graphics;
      
    exports passwordGame;
    exports passwordGame.grafica;
    exports passwordGame.model;

    opens passwordGame to javafx;
    opens passwordGame.grafica to javafx;
}