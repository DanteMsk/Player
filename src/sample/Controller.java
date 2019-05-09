package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import sample.Animation.Position;

public class Controller {
    private HashMap<String, File> hashMap = new HashMap<>();

    /**
     * Данная таблица хранит файл для воспроизведения как
     * значение, а его короткое иммя как ключ
     * данный способ выбран бля удобного получения файла через его имя
     **/

    private Mp3Player mp3Player = new Mp3Player();     // Объект типа mp3Player для работы с воспроизведением файлов

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button play;

    @FXML
    private Button add;

    @FXML
    private Button search;

    @FXML
    private Button previousSounds;

    @FXML
    private Button stop;

    @FXML
    private TextField searchSong;

    @FXML
    private Button delete;

    @FXML
    private Button pause;

    @FXML
    private Button nextSong;

    @FXML
    private Slider sliderSoundVolume;

    @FXML
    private ListView<String> listSounds;

    @FXML
    void openFileChooser(ActionEvent event) {           // Открытие fileChooser при добавлении трека
        searchSong.setText(null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:/Users/Илья/Downloads"));   // Место открытия по умолчанию
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.mp3"));
        List<File> listFile = fileChooser.showOpenMultipleDialog(null);
        for (File file : listFile) {
            if (hashMap.containsValue(file)) {          // Проверка есть ли трек уже в таблице, чтобы не добавлять существующий

                //   searchSong.setText("Трек " + file.getName() + " уже добавлен");

            } else {
                hashMap.put(file.getName(), file);      // Добавляются все выбранные файлы в лист и таблицу
                listSounds.getItems().addAll(file.getName());
                listSounds.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
        }
    }

    @FXML
    void playMusic(ActionEvent event) {
        try {
            searchSong.setText(null);
            if (listSounds.getItems().isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
                Position searchSongPosition = new Position();
                searchSongPosition.changePositionX(searchSong);
                searchSong.setText("Трек не выбран");                       // Стандартная проверка на наличие трека на панели
            } else {
                pause.setOpacity(1);                                        // Меняется оттенок кнопок при нажатии на них;
                play.setOpacity(0.7);
                File file = hashMap.get(listSounds.getSelectionModel().getSelectedItem());     // Выбирается нужный файл и воспроизводится
                mp3Player.play(file);
                mp3Player.setValue((int) sliderSoundVolume.getValue(), (int) sliderSoundVolume.getMax());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void DeleteSound(ActionEvent event) { // FIXME: 09.05.2019 не удаляет последний трек в листе
        searchSong.setText(null);
        if (listSounds.getItems().isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
            searchSong.setText("Трек не выбран");
            Position changePositionDeletButton = new Position();
            changePositionDeletButton.changePositionX(searchSong);
        } else {
            pause.setOpacity(1);
            play.setOpacity(1);
            String deleteSoundName = hashMap.get(listSounds.getSelectionModel().getSelectedItem()).getName();
            if ((mp3Player.getCurrentFileName() != null) && (mp3Player.getCurrentFileName().equals(deleteSoundName))) {
                try {
                    mp3Player.getPlayer().stop();
                } catch (BasicPlayerException e) {
                    e.printStackTrace();
                }
            } else {
                listSounds.getItems().remove(listSounds.getSelectionModel().getSelectedItem()); // Удаление трека из листа и карты
                hashMap.remove(deleteSoundName);
            }
        }
    }

    @FXML
    void prevSound(ActionEvent event) { //FIXME: 09.05.2019    немножно криво работает на крайних с конца и начала треков в листе
        searchSong.setText(null);
        if (hashMap.isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
            searchSong.setText("Трек не выбран");
            Position changePositionDeleteButton = new Position();
            changePositionDeleteButton.changePositionX(searchSong);
        } else {
            listSounds.getSelectionModel().selectPrevious();
            String currentSound2 = listSounds.getSelectionModel().getSelectedItem();
            searchSong.setText(currentSound2);
            mp3Player.play(hashMap.get(currentSound2));
        }
    }

    @FXML
    void searchSound(ActionEvent event) {   // Поиск трека по имени
        if (searchSong.getText() != null) {
            String ChooseTrack = searchSong.getText();
            if (hashMap.containsKey(ChooseTrack)) {
                mp3Player.play(hashMap.get(ChooseTrack));
                listSounds.getFocusModel().getFocusedItem();
            } else searchSong.setText("Не найдено");
        }
    }


    @FXML
    void nextSound(ActionEvent event) {   // FIXME: 09.05.2019    немножно криво работает на крайних с конца и начала треков в листе
        searchSong.setText(null);
        if (hashMap.isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
            searchSong.setText("Трек не выбран");
            Position changePositionDeleteButton = new Position();
            changePositionDeleteButton.changePositionX(searchSong);
        } else {
            listSounds.getSelectionModel().selectNext();
            String currentSound2 = listSounds.getSelectionModel().getSelectedItem();
            searchSong.setText(currentSound2);
            mp3Player.play(hashMap.get(currentSound2));

        }
    }

    @FXML
    public void stopMusic(ActionEvent event) {
        if (hashMap.isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
            searchSong.setText("Трек не выбран");
            Position changePositionDeleteButton = new Position();
            changePositionDeleteButton.changePositionX(searchSong);
        } else {
            if (mp3Player.getPlayer().getStatus() == BasicPlayer.STOPPED) {
                Position changePositionStopButton = new Position();
                changePositionStopButton.changePositionX(stop);
            } else
                pause.setOpacity(1);
            play.setOpacity(1);
            mp3Player.stop();
        }
    }

    @FXML
    public void pauseMusic(ActionEvent event) {
        if (hashMap.isEmpty() || listSounds.getSelectionModel().getSelectedItems().isEmpty()) {
            searchSong.setText("Трек не выбран");
            Position changePositionDeleteButton = new Position();
            changePositionDeleteButton.changePositionX(searchSong);
        } else {
            pause.setOpacity(0.7);
            play.setOpacity(1);
            if (mp3Player.getPlayer().getStatus() == BasicPlayer.PAUSED) {
                Position changePositionPauseButton = new Position();
                changePositionPauseButton.changePositionX(pause);
            } else
                mp3Player.pause();
        }
    }

    @FXML
    public void sliderMouseEvent(MouseEvent mouseEvent) {                   // Регулирование громкости
        double currentValueSlider = sliderSoundVolume.getValue();
        double maxValueSlider = sliderSoundVolume.getMax();
        mp3Player.setValue((int) currentValueSlider, (int) maxValueSlider);
    }
}