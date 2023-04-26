package PAssign08;

/*
 * File: PAssign08.java
 * Class: CSCI 1302
 * Author: Ashley Zimerle
 * Date Created: 22 April 2023
 * Last Modified: 26 April 2023
 * Description: Modifies PAssign07.java to be interactive
 * Link to Github hello-world tutorial: https://github.com/ashzim/hello-world
 */

import java.io.File;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PAssign08 extends Application {
	// Create nodes / data members accessible in methods and KeyPadCustomPane
	private File imgTimer = new File("src/PAssign07/PAssign07 Images/btnBlank1.png");
	private Image imgBtnBlank1 = new Image(imgTimer.toURI().toString());
	private ImageView imageViewBtnBlank1 = new ImageView(imgBtnBlank1);

	private File imgHourglass = new File("src/PAssign07/PAssign07 Images/btnBlank2.png");
	private Image imgBtnBlank2 = new Image(imgHourglass.toURI().toString());
	private ImageView imageViewBtnBlank2 = new ImageView(imgBtnBlank2);

	private File alarmOption = new File("src/PAssign08/PAssign08 Images/imgAlarmOption.png");
	private Image imgAlarmOption = new Image(alarmOption.toURI().toString());
	private ImageView imageViewAlarmOption = new ImageView(imgAlarmOption);
	
	private File noOption = new File("src/PAssign08/PAssign08 Images/imgBlank.png");
	private Image imgNoOption = new Image(noOption.toURI().toString());
	private ImageView imageViewNoOption = new ImageView(imgNoOption);

	private String defaultTime = "00:00";
	private String storedTime = defaultTime;
	private String storedRPM = "0000";
	private String currentRPMDigits = "";
	private String currentTimerDigits = "";
	private	TextField tfTimer = new TextField(defaultTime);
	private Label rTimer = new Label("");
	private Integer totalSeconds;
	private Integer remainingMinutes;
	private Integer remainingSeconds; 
	private boolean rpmMode;

	@Override
	public void start(Stage primaryStage) {
		// Create Images & ImageViews
		File imgCA = new File("src/PAssign07/PAssign07 Images/lblCA.png");
		Image imgLblCA = new Image(imgCA.toURI().toString());

		File imgSS = new File("src/PAssign07/PAssign07 Images/btnStartStop.png");
		Image imgBtnStartStop = new Image(imgSS.toURI().toString());

		File imgOL = new File("src/PAssign07/PAssign07 Images/btnOpenLid.png");
		Image imgBtnOpenLid = new Image(imgOL.toURI().toString());

		File imgBrakeWhite = new File("src/PAssign07/PAssign07 Images/btnBrake.png");
		Image imgBtnBrakeWhite = new Image(imgBrakeWhite.toURI().toString());

		File imgAlarm = new File("src/PAssign07/PAssign07 Images/btnAlarm.png");
		Image imgBtnAlarm = new Image(imgAlarm.toURI().toString());

		File imgRPM = new File("src/PAssign07/PAssign07 Images/btnRPM.png");
		Image imgBtnRPM = new Image(imgRPM.toURI().toString());

		File imgBrakeBlue = new File("src/PAssign07/PAssign07 Images/imgBrake.png");
		Image imageBrakeBlue = new Image(imgBrakeBlue.toURI().toString());
		ImageView imageViewBrakeBlue = new ImageView(imageBrakeBlue);

		// Create Labels for logos
		Label lblBD = new Label(String.format("BECTON%nDICKINSON"));
		lblBD.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 10));
		lblBD.setTextAlignment(TextAlignment.RIGHT);

		Label lblCA = new Label(String.format("CLAY ADAMS\u00AE  "));
		lblCA.setFont(Font.font("Verdana", 22));
		lblCA.setGraphic(new ImageView(imgLblCA));
		lblCA.setContentDisplay(ContentDisplay.RIGHT);

		Label lblSeroFuge = new Label(String.format("SERO-FUGE\u00AE 2001"));
		lblSeroFuge.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));

		// Create TextField for timer / display
		tfTimer.setMinSize(190, 75);
		tfTimer.setMaxSize(190, 75);
		tfTimer.setFont(Font.font("alarm clock", 50));
		tfTimer.setBackground(Background.fill(Color.TRANSPARENT));
		tfTimer.setAlignment(Pos.CENTER);
		tfTimer.setEditable(false);

		// Create Shape & Label to surround tfTimer
		rTimer.setMinSize(230, 75);
		rTimer.setMaxSize(230, 75);
		rTimer.setBackground(Background.fill(Color.DIMGREY));
		rTimer.setGraphic(imageViewNoOption);

		Rectangle rTimerOutline = new Rectangle(250, 100);
		rTimerOutline.setArcHeight(15);
		rTimerOutline.setArcWidth(15);
		rTimerOutline.setFill(Color.valueOf("1A91AE"));
		rTimerOutline.setStroke(Color.BLACK);
		rTimerOutline.setStrokeWidth(2);

		// Create Lines for lblSerFuge logo
		Line line1 = new Line(200, 0, 270, 0);
		Line line2 = new Line(10, 15, 270, 15);

		// Create additional Buttons
		Button[] optionButtons = {
				new Button("", new ImageView(imgBtnStartStop)), new Button("", new ImageView(imgBtnOpenLid)), new Button("", new ImageView(imgBtnBrakeWhite)), 
				new Button("", new ImageView(imgBtnAlarm)), new Button("", new ImageView(imgBtnRPM))
		};

		for (int i = 0; i < optionButtons.length; i++) {
			optionButtons[i].setBorder(Border.stroke(Color.BLACK));
			optionButtons[i].setBackground(Background.fill(Color.valueOf("1A91AE")));
			optionButtons[i].setPadding(new Insets(10));
		}

		// Set EventHandlers for additional Buttons
		// Start/Stop button
		optionButtons[0].setOnAction(e -> countdown());

		// Open Lid button
		optionButtons[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!tfTimer.getText().equals("OPEN")) {
					tfTimer.setText("OPEN");
					rTimer.setText("");
				} else {
					tfTimer.setText(defaultTime);
				}
			}
		});

		// Brake button
		optionButtons[2].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!rpmMode) {
					// Add brake text to rTimer depending on alarm status
					if (!rTimer.getText().equals("BRAKE") && !rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setText("BRAKE");
						rTimer.setFont(Font.font("alarm clock", 15));
						rTimer.setAlignment(Pos.CENTER_LEFT);
						rTimer.setContentDisplay(ContentDisplay.TEXT_ONLY);
					} else if (rTimer.getText().equals("BRAKE") && !rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setText("");				
					} else if (rTimer.getText().equals("BRAKE") && rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setText("");
						rTimer.setAlignment(Pos.CENTER_LEFT);
						rTimer.setContentDisplay(ContentDisplay.LEFT);
					} else if (!rTimer.getText().equals("BRAKE") && rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setText("BRAKE");
						rTimer.setFont(Font.font("alarm clock", 15));
						rTimer.setAlignment(Pos.CENTER_LEFT);
						rTimer.setContentDisplay(ContentDisplay.BOTTOM);
					}
				}
			}
		});

		// Alarm button
		optionButtons[3].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!rpmMode) {
					// Add alarm image to rTimer depending on brake status
					if (!rTimer.getText().equals("BRAKE") && !rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setGraphic(imageViewAlarmOption);
						rTimer.setContentDisplay(ContentDisplay.LEFT);
					} else if (rTimer.getText().equals("BRAKE") && !rTimer.getGraphic().equals(imageViewAlarmOption)){
						rTimer.setGraphic(imageViewAlarmOption);
						rTimer.setContentDisplay(ContentDisplay.BOTTOM);
					} else if (rTimer.getText().equals("BRAKE") && rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setText("BRAKE");
						rTimer.setFont(Font.font("alarm clock", 15));
						rTimer.setAlignment(Pos.CENTER_LEFT);
						rTimer.setGraphic(imageViewNoOption);
						rTimer.setContentDisplay(ContentDisplay.TEXT_ONLY);
					} else if (!rTimer.getText().equals("BRAKE") && rTimer.getGraphic().equals(imageViewAlarmOption)) {
						rTimer.setGraphic(imageViewNoOption);
						rTimer.setContentDisplay(ContentDisplay.LEFT);
					}
				}
			}
		});

		// RPM button
		optionButtons[4].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// Set rpmMode to true & add text to rTimer
				if (!rpmMode) {
					rpmMode = true;
					rTimer.setText("RPM");
					rTimer.setFont(Font.font("alarm clock", 15));
					rTimer.setAlignment(Pos.CENTER_RIGHT);
					rTimer.setGraphic(imageViewNoOption);
					rTimer.setContentDisplay(ContentDisplay.TEXT_ONLY);
					tfTimer.setText(storedRPM);
				} else {
					rpmMode = false;
					rTimer.setText("");
					tfTimer.setText(defaultTime);
				}
			}
		});

		// Create KeyPadPane
		KeyPadCustomPane keyPadPane = new KeyPadCustomPane();
		keyPadPane.setHgap(10);
		keyPadPane.setVgap(10);
		keyPadPane.setPadding(new Insets(15));
		keyPadPane.setAlignment(Pos.CENTER);

		// Create Panes to organize elements
		StackPane timerPane = new StackPane();
		timerPane.getChildren().addAll(rTimerOutline, rTimer, tfTimer);
		timerPane.setAlignment(Pos.CENTER);

		VBox seroFugeVBox = new VBox(10);
		seroFugeVBox.getChildren().addAll(line1, line2, lblSeroFuge);
		seroFugeVBox.setAlignment(Pos.CENTER_RIGHT);

		HBox startStopPane = new HBox(10);
		startStopPane.getChildren().addAll(optionButtons[0], optionButtons[1]);
		startStopPane.setAlignment(Pos.CENTER);

		// Create GridPane for option Buttons outside KeyPadPanePassign07
		GridPane optionsPane = new GridPane();
		optionsPane.add(lblCA, 1, 0);
		optionsPane.add(imageViewBrakeBlue, 0, 1);
		optionsPane.add(timerPane, 1, 1);
		optionsPane.add(optionButtons[2], 2, 1);
		optionsPane.add(seroFugeVBox, 1, 2);
		optionsPane.add(optionButtons[3], 2, 2);
		optionsPane.add(startStopPane, 1, 3);
		optionsPane.add(optionButtons[4], 2, 3);
		optionsPane.setPadding(new Insets(10));
		optionsPane.setHgap(15);
		optionsPane.setVgap(10);
		optionsPane.setAlignment(Pos.CENTER_LEFT);

		// Create BorderPane for mainPane
		BorderPane mainPane = new BorderPane();
		mainPane.setBackground(Background.fill(Color.valueOf("1A91AE")));
		mainPane.setBorder(Border.stroke(Color.BLACK));
		mainPane.setLeft(keyPadPane);
		mainPane.setCenter(optionsPane);
		mainPane.setBottom(lblBD);

		// Create Scene
		Scene scene = new Scene(mainPane, 800, 400);

		// Set Stage
		primaryStage.setTitle("PAssign08");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void addDigit(String digit) {		
		// Determine if in rpmMode
		if (rpmMode) {
			if (currentRPMDigits.length() < 4) {
				currentRPMDigits += digit;
				storedRPM = currentRPMDigits;
				tfTimer.setText(storedRPM);
			}
		} else {
			if (currentTimerDigits.length() < 5) {
				if (currentTimerDigits.length() < 2) {
					currentTimerDigits += digit;	
				} else if (currentTimerDigits.length() == 2 && !currentTimerDigits.contains(":")) { // Add colon to correct position in String
					currentTimerDigits += ":" + digit;
				} else if (currentTimerDigits.length() > 2 && currentTimerDigits.contains(":")){ 
					currentTimerDigits += digit;
				}
				
				// Store entered number and display on tfTimer
				storedTime = currentTimerDigits;
				tfTimer.setText(storedTime);
			}
		}
	}

	// Count down to zero
	public void countdown() {
		// Instantiate Timeline for countdown
		Timeline tlCountdown = new Timeline();
		tlCountdown.setCycleCount(Timeline.INDEFINITE);

		// Determine if in rpmMode
		if (rpmMode) {
			rpmMode = false;
			rTimer.setText("");
			tfTimer.setText(storedTime);
		} else {
			if (storedTime.length() < 5) {
				tfTimer.setText("ERROR");
			} else {
				// Convert storedTime to integer value for countdown & calculate remainingMinutes / remainingSeconds / totalSeconds
				remainingMinutes = Integer.parseInt(storedTime.replace(":", "")) / 100;
				remainingSeconds = Integer.parseInt(storedTime.replace(":", "")) % 100;
				totalSeconds = (remainingMinutes * 60) + remainingSeconds;
				
				// Instantiate KeyFrame to animate countdown on tfTimer
				KeyFrame kfCountdown = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						char[] digitArray = new char[4];
							
							if (remainingMinutes >= 10 && remainingSeconds >= 10) {
								digitArray[0] = remainingMinutes.toString().charAt(0);
								digitArray[1] = remainingMinutes.toString().charAt(1);
								digitArray[2] = remainingSeconds.toString().charAt(0);
								digitArray[3] = remainingSeconds.toString().charAt(1);
							} else if (remainingMinutes >= 10 && remainingSeconds >= 0 && remainingSeconds < 10) {
								digitArray[0] = remainingMinutes.toString().charAt(0);
								digitArray[1] = remainingMinutes.toString().charAt(1);
								digitArray[2] = '0';
								digitArray[3] = remainingSeconds.toString().charAt(0);
							} else if (remainingMinutes >= 0 && remainingMinutes < 10 && remainingSeconds >= 10) {
								digitArray[0] = '0';
								digitArray[1] = remainingMinutes.toString().charAt(0);
								digitArray[2] = remainingSeconds.toString().charAt(0);
								digitArray[3] = remainingSeconds.toString().charAt(1);
							} else if (remainingMinutes >= 0 && remainingMinutes < 10 && remainingSeconds >=0 && remainingSeconds < 10) {
								digitArray[0] = '0';
								digitArray[1] = remainingMinutes.toString().charAt(0);
								digitArray[2] = '0';
								digitArray[3] = remainingSeconds.toString().charAt(0);
							} else {
								digitArray[0] = '0';
								digitArray[1] = '0';
								digitArray[2] = '0';
								digitArray[3] = remainingSeconds.toString().charAt(0);
							}
							
							totalSeconds--;	
							remainingMinutes = totalSeconds / 60;
							remainingSeconds = totalSeconds % 60;
							
							tfTimer.setText("" + digitArray[0] + digitArray[1] + ":" + digitArray[2] + digitArray[3]);
						
						// Display "STOP" on tfTimer when time runs out
						if (totalSeconds < 0) {
							tlCountdown.stop();
							tfTimer.setText("STOP");
						}
					}
				});

				tlCountdown.getKeyFrames().add(kfCountdown);
				tlCountdown.playFromStart();
			}
		}
	}

	class KeyPadCustomPane extends KeyPadPane {
		// Default, no-arg constructor
		public KeyPadCustomPane() {
			// Call default constructor for KeyPadPane
			super();

			// Change text on blank buttons
			btnBlank1.setGraphic(imageViewBtnBlank1);
			btnBlank1.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			imageViewBtnBlank1.fitHeightProperty().bind(btnBlank1.heightProperty());;
			imageViewBtnBlank1.fitWidthProperty().bind(btnBlank1.widthProperty());;

			btnBlank2.setGraphic(imageViewBtnBlank2);
			btnBlank2.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			imageViewBtnBlank2.fitHeightProperty().bind(btnBlank2.heightProperty());;
			imageViewBtnBlank2.fitWidthProperty().bind(btnBlank2.widthProperty());;

			// Modify Button appearance
			for (int i = 0; i < listButtons.size(); i++) {
				listButtons.get(i).setMinSize(75, 75);
				listButtons.get(i).setMaxSize(75, 75);
				listButtons.get(i).setFont(Font.font("Helvetica", 25));
				listButtons.get(i).setBackground(Background.fill(Color.WHITE));
			}
		}

		@Override
		protected void registerEventHandlers() {
			// Set timer / RPM when number buttons are pressed
			for (int i = 0; i < listButtons.size() - 2 || i == 10; i++) {
				final int INDEX = i;
				listButtons.get(i).setOnAction(e -> addDigit(listButtons.get(INDEX).getText()));
			}

			// Reset timer when btnBlank1 selected
			listButtons.get(9).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					tfTimer.setText(defaultTime);
					rTimer.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				}
			});

			// Recall stored time when btnBlank2 selected
			listButtons.get(11).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					tfTimer.setText(storedTime);
				}
			});
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

