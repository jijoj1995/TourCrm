/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gn.global.util;

import com.gn.App;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.CoreLeadNotesDto;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/01/2019
 */
@SuppressWarnings("unchecked")
 public class NotesDialog {

    private static Color color;
    private static JFXDialog dialog = new JFXDialog();

    private static final EventHandler<MouseEvent> close = event -> dialog.close();

    public enum Type { INFO, WARNING, ERROR, SUCCESS }
    public enum ButtonType { OK, CANCEL }

   public static void createAlert(Type type, String title, ObservableList<CoreLeadNotesDto> notesData) {
        createLayout(createHeader(type), createContent(title, notesData), createActions(type, new EventHandler[]{
                close
        }));
    }

    private static void createLayout(VBox header, VBox content, HBox actions){
        StackPane root = new StackPane();
        root.setPadding(new Insets(10));

        VBox box = new VBox();
        box.setStyle("-fx-background-radius : 10 0 10 0; -fx-background-color : white;");

        box.getChildren().addAll(header, content, actions);
        root.getChildren().add(box);

        show(root);
    }

    private static VBox createHeader(Type type){
        VBox header = new VBox();
        header.setPrefHeight(60);
        header.setAlignment(Pos.CENTER);

        Label headerLabel=new Label("Notes Section");
        headerLabel.getStyleClass().add("h2");
        headerLabel.setTextFill(Color.WHITE);

        header.setBackground(new Background(new BackgroundFill(Color.web("#33B5E5"), new CornerRadii(10, 0, 0, 0,false), Insets.EMPTY)));
        header.getChildren().add(headerLabel);
        return header;
    }

    private static VBox  createContent(String title, ObservableList<CoreLeadNotesDto> notesData){
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20D);
        VBox.setMargin(container, new Insets(10,0,0,0));
        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("h2");
        container.getChildren().addAll(lblTitle,createVbox(notesData) );

        return container;
    }

    private static HBox createActions(Type type, EventHandler<MouseEvent>[] event){
        HBox actions = new HBox();
        actions.setMinSize(480, 73);
        actions.setAlignment(Pos.CENTER);
        VBox.setMargin(actions, new Insets(10, 0, 0, 10));
        actions.setSpacing(5D);

        ArrayList<EventHandler<MouseEvent>> list = new ArrayList<>(Arrays.asList(event));

        switch (type) {
//            case WARNING:
//                actions.getChildren().add(
//                            createButton(ButtonType.CANCEL, "Cancel", close));
//                break;
            default:
                actions.getChildren().add(createButton(ButtonType.OK,"OK", list.get(0)));
                break;
        }
        return actions;
    }

    private static Button createButton(ButtonType type, String text, EventHandler<MouseEvent> eventEventHandler){
        Button button = new Button(text);
        button.setCursor(Cursor.HAND);
        button.setOnMouseReleased(eventEventHandler);
        button.setPrefWidth(100);
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, close);
        switch (type){
            case CANCEL:
                button.setDefaultButton(true);
                break;
            case OK:
                button.setDefaultButton(true);
                break;
        }
        return button;
    }

    private static void show(Region region){
        dialog.setDialogContainer(App.getDecorator().getBackground());
        dialog.setContent(region);
        dialog.setTransitionType(JFXDialog.DialogTransition.TOP);

        Platform.runLater(() -> new Thread(new Task() {
            @Override
            protected Object call() {
                dialog.show();
                return null;
            }
        }).start());
    }

    private static VBox createVbox(ObservableList<CoreLeadNotesDto> notesData){
        VBox vBox = new VBox();
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.TOP_CENTER);
        Label label = new Label("Notes Section");
        label.setFont(Font.font("", FontWeight.BOLD, 16));

        JFXTextArea jfxTextArea = new JFXTextArea();

        Button addButton = new Button("Add");
        addButton.getStyleClass().add("buttonPrimary");
        addButton.setCursor(Cursor.HAND);

        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("buttonPrimary");
        closeButton.setOnAction(event -> {dialog.close();});

        addButton.setOnAction(event -> {
            if (!jfxTextArea.getText().isEmpty())notesData.add(new CoreLeadNotesDto(null, jfxTextArea.getText()));
            jfxTextArea.setText("");
            jfxTextArea.requestFocus();

        });
        TableView tableView = new TableView();
        TableColumn notesColumn = new TableColumn("Notes");
        TableColumn<CoreLeadNotesDto, CoreLeadNotesDto> deleteColumn = new TableColumn<>("Action");

        tableView.getColumns().addAll(notesColumn,deleteColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        notesColumn.setCellValueFactory(new PropertyValueFactory<CoreLeadNotesDto, String>("notesData"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("Action"));
        deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        deleteColumn.setCellFactory(param -> new TableCell<CoreLeadNotesDto, CoreLeadNotesDto>() {
            FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.REMOVE);

            @Override
            protected void updateItem(CoreLeadNotesDto item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }
                deleteButton.setCursor(Cursor.HAND);
                deleteButton.setGlyphSize(30);
                setGraphic(deleteButton);
                deleteButton.setOnMouseClicked(event -> {notesData.remove(item);});
            }
        });
        HBox buttonHbox=new HBox();

        buttonHbox.getChildren().add(addButton);
        buttonHbox.getChildren().add(closeButton);
        buttonHbox.setAlignment(Pos.TOP_CENTER);
        buttonHbox.setSpacing(20);

        tableView.setItems(notesData);

        vBox.getChildren().add(label);
        vBox.getChildren().add(jfxTextArea);
        vBox.getChildren().add(buttonHbox);
        vBox.getChildren().add(tableView);
        vBox.setPrefSize(400,350);

        return vBox;
    }
}
