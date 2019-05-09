package sample;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.io.File;

public class Mp3Player {                            // Данный класс реализует объект типа BasicPlayer для управлением звоковоспроизведения
    private BasicPlayer player = new BasicPlayer();

    public String getCurrentFileName() {
        return currentFileName;
    }

    private double currentVolume;                  // Текущее значение звука
    private String currentFileName;                // Текущий файл

    public BasicPlayer getPlayer() {
        return player;
    }

    public void play(File _file) {
        try {
            if (currentFileName != null && currentFileName.equals(_file.getName()) && player.getStatus() == BasicPlayer.PAUSED) {

                /** Условие для возобновления игры
                 * трека после паузы
                 * тут проверяется статус воспроизведения**/

                player.resume();
            } else {
                currentFileName = _file.getName();
                player.open(_file);                         // Открытие нового трека и начало проигрывания
                player.play();
                player.setGain(currentVolume);
            }


        } catch (BasicPlayerException e) {
            //   e.printStackTrace();
        }
    }

    public void pause() {
        try {
            player.pause();
        } catch (BasicPlayerException e) {
            //   e.printStackTrace();
        }
    }

    public void stop() {
        try {
            player.stop();
        } catch (BasicPlayerException e) {
            // e.printStackTrace();
        }
    }

    public void setValue(double _currentVolume, double _maxVolume) {                // Установка звука
        try {
            currentVolume = _currentVolume;
            if (currentVolume == 0) {
                player.setGain(0);
            }else {
                player.setGain(calValueVolume(_currentVolume,_maxVolume));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private double calValueVolume(double _currentValue, double _maxValue ){        // Подсчет звука
        currentVolume = _currentValue / _maxValue;
        return currentVolume;
    }
}

