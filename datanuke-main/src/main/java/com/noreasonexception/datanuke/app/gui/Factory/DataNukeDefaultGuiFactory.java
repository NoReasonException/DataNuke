package com.noreasonexception.datanuke.app.gui.Factory;

import com.noreasonexception.datanuke.app.factory.DataNukeAbstractFactory;
import com.noreasonexception.datanuke.app.gui.LeftBorder.ClassesTable;
import com.noreasonexception.datanuke.app.gui.Menu.MainMenu;
import com.noreasonexception.datanuke.app.gui.RightBorder.OptionsTable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
public class DataNukeDefaultGuiFactory extends DataNukeAbstractGuiFactory {


    public DataNukeDefaultGuiFactory(DataNukeAbstractFactory coreFactory) {
        super(coreFactory);
    }

    @Override
    public Node getTopBorder() {
        return new MainMenu();
    }

    @Override
    public Node getLeftBorder() {
        ClassesTable classesTable = new ClassesTable();
        //make sure that ClassesTable will be able to hear about core changes...
        getCoreFactory().getThreadRunner().subscribeStateListener(classesTable.getCoreStateListener());
        getCoreFactory().getThreadRunner().subscribeTaskListener(classesTable.getCoreTaskListener());
        return classesTable;
    }

    @Override
    public Node getCenterBorder() {

        final AreaChart<Number,Number> ac =
                new AreaChart<Number,Number>(new NumberAxis(1,31,1),new NumberAxis());
        XYChart.Series seriesApril= new XYChart.Series();
        seriesApril.setName("April");
        seriesApril.getData().add(new XYChart.Data(1, 4));
        seriesApril.getData().add(new XYChart.Data(3, 10));
        seriesApril.getData().add(new XYChart.Data(6, 15));
        seriesApril.getData().add(new XYChart.Data(9, 8));
        seriesApril.getData().add(new XYChart.Data(12, 5));
        seriesApril.getData().add(new XYChart.Data(15, 18));
        seriesApril.getData().add(new XYChart.Data(18, 15));
        seriesApril.getData().add(new XYChart.Data(21, 13));
        seriesApril.getData().add(new XYChart.Data(24, 19));
        seriesApril.getData().add(new XYChart.Data(27, 21));
        seriesApril.getData().add(new XYChart.Data(30, 21));
        ac.getData().add(seriesApril);
        return ac;

    }

    @Override
    public Node getBottomBorder() {
        return null;
    }

    @Override
    public Node getRightBorder() {
        OptionsTable table=new OptionsTable(this);
        getCoreFactory().getThreadRunner().subscribeStateListener(table.getOnOffSwitchStateListener());
        getCoreFactory().getThreadRunner().subscribeStateListener(table.getNextEventStateListener());
        getCoreFactory().getThreadRunner().subscribeTaskListener(table.getNextEventTaskListener());
        return table;

    }
}
