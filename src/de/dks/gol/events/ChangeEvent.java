package de.dks.gol.events;

import de.dks.gol.core.MainApp;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *
 * @author David Kunschke alias @dks
 * 
 * <p>
 * Diese Klasse stellt alle Elemente die auf veränderung 
 * Reagieren sollen in ihren einzelenen Funktionen dar
 * </p>
 * 
 */
public class ChangeEvent {

    public ChangeEvent() {
    }
    /**
     * sliderTimeChangeEvent prüft bewegt sich unser TimeSlider dann übergibt er den neuen Wert an unseren 
     * SimulationHandler und setzt die PAUSEDTIME neu
     * 
     * @param mainApp
     * @param slider
     * @param label 
     */
    public void sliderTimeChangeEvent(final MainApp mainApp, Slider slider, final Label label) {
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            double new_time = (double) new_val;
            mainApp.getSceneHandler().getSimualtionHandler().setPAUSEDTIME((int) new_time);
            label.setText(String.valueOf(mainApp.getSceneHandler().getSimualtionHandler().getPAUSEDTIME()));
        });
    }
    /**
     *  sliderCellCSizeChangeEvent prüft bewegt sich unser CellSizeSlider dann übergibt er den neuen Wert an unseren 
     * SimulationHandler und setzt die CELLLSIZE neu
     * 
     * @param mainApp
     * @param slider
     * @param label 
     */
    public void sliderCellCSizeChangeEvent(final MainApp mainApp, Slider slider, final Label label) {
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            double new_size = (double) new_val;
            mainApp.getSceneHandler().getSimualtionHandler().setCellSize((int) new_size);
            label.setText(String.valueOf(mainApp.getSceneHandler().getSimualtionHandler().getCellSize()));
        });
    }
    /**
     * checkboxColorChangeEvent prüft ist CheckBox aktiv oder nicht dann übergibt er den neuen Wert an unseren 
     * SimulationHandler und setzt das IsColor On/Off neu
     * @param mainApp
     * @param colorCheckBox 
     */
    public void checkboxColorChangeEvent(final MainApp mainApp, CheckBox colorCheckBox) {
        colorCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            mainApp.getSceneHandler().getSimualtionHandler().setIsColorOn(new_val);
        });
    }
    /**
     * checkboxOlderChangeEvent prüft ist CheckBox aktiv oder nicht dann übergibt er den neuen Wert an unseren 
     * SimulationHandler und setzt das IsOlder On/Off neu
     * @param mainApp
     * @param olderCheckBox 
     */
    public void checkboxOlderChangeEvent(final MainApp mainApp, CheckBox olderCheckBox) {
        olderCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            mainApp.getSceneHandler().getSimualtionHandler().setIsOlderOn(new_val);
        });
    }
    /**
     * checkboxGridChangeEvent prüft ist CheckBox aktiv oder nicht dann übergibt er den neuen Wert an unseren 
     * SimulationHandler und setzt das CellGrid On/Off neu
     * @param mainApp
     * @param gridCheckBox 
     */
    public void checkboxGridChangeEvent(final MainApp mainApp, CheckBox gridCheckBox) {
        gridCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            mainApp.getSceneHandler().getSimualtionHandler().setIsGridOn(new_val);
        });
    }
}
