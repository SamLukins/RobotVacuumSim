package robotvacuum.gui;

import robotvacuum.collision.CollisionRectangle;
import robotvacuum.collision.Position;
import robotvacuum.house.House;
import robotvacuum.house.HouseManager;
import robotvacuum.house.Room;
import robotvacuum.house.Wall;
import robotvacuum.simulation.Simulator;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Austen Seidler
 */
public class HouseEditor extends javax.swing.JFrame {

    HouseManager h;
    Simulator s;
    HouseGUI gui;
    boolean moveVacuum, notStartedYet;
    Thread t1;

    /**
     * Creates new form HouseEditor
     */
    public HouseEditor() {
        initComponents();
        h = new HouseManager();
        gui = new HouseGUI();
        s = null;
        moveVacuum = true;
        notStartedYet = true;
        t1 = new Thread(() -> {
            int i = 0;
            while (true) {
                while (moveVacuum) {
                    long sleepTime = s.movement();
                    cleanPercentText.setText("Percent cleaned: " + (int)s.getCleanPercent() + "%");
                    gui.redoVacuum(s.getVacuumShape());
                    try {
                        int defaultSpeed = 5;
                        Thread.sleep(sleepTime/ defaultSpeed);
//                        Thread.sleep(100);
//                    Thread.sleep(distance/s.);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                    if (i == 10) {
                        gui.paintCleanSpots(s.getCleanSpots());
                        i = 0;
                    }
                }
                Thread.onSpinWait();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        newHouseButton = new javax.swing.JButton();
        loadHouseButton = new javax.swing.JButton();
        loadHouseField = new javax.swing.JTextField();
        saveHouseButton = new javax.swing.JButton();
        saveHouseField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        addRoomButton = new javax.swing.JButton();
        addChestButton = new javax.swing.JButton();
        addTableButton = new javax.swing.JButton();
        deleteRoomButton = new javax.swing.JButton();
        deleteTableButton = new javax.swing.JButton();
        deleteChestButton = new javax.swing.JButton();
        outputText = new javax.swing.JLabel();
        createVacuumButton = new javax.swing.JButton();
        startVacuumButton = new javax.swing.JButton();
        pauseVacuumButton = new javax.swing.JButton();
        deleteVacuumButton = new javax.swing.JButton();
        cleanableAreaText = new javax.swing.JLabel();
        cleanPercentText = new javax.swing.JLabel();
        stopVacuumButton = new javax.swing.JButton();
        loadResultsButton = new javax.swing.JButton();
        saveResultsButton = new javax.swing.JButton();
        loadResultsField = new javax.swing.JTextField();
        saveResultsField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("House Editor");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        newHouseButton.setText("Create new house");
        newHouseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newHouseButtonMouseClicked(evt);
            }
        });

        loadHouseButton.setText("Load saved house");
        loadHouseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadHouseButtonMouseClicked(evt);
            }
        });

        saveHouseButton.setText("Save current house");
        saveHouseButton.setEnabled(false);
        saveHouseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveHouseButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newHouseButton)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loadHouseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveHouseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadHouseField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveHouseField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newHouseButton)
                    .addComponent(loadHouseButton)
                    .addComponent(loadHouseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveHouseButton)
                    .addComponent(saveHouseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addRoomButton.setText("Add room");
        addRoomButton.setEnabled(false);
        addRoomButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRoomButtonMouseClicked(evt);
            }
        });

        addChestButton.setText("Add chest");
        addChestButton.setEnabled(false);
        addChestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addChestButtonMouseClicked(evt);
            }
        });

        addTableButton.setText("Add table");
        addTableButton.setEnabled(false);
        addTableButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTableButtonMouseClicked(evt);
            }
        });

        deleteRoomButton.setText("Delete room");
        deleteRoomButton.setEnabled(false);
        deleteRoomButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteRoomButtonMouseClicked(evt);
            }
        });

        deleteTableButton.setText("Delete table");
        deleteTableButton.setEnabled(false);
        deleteTableButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteTableButtonMouseClicked(evt);
            }
        });

        deleteChestButton.setText("Delete chest");
        deleteChestButton.setEnabled(false);
        deleteChestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteChestButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteRoomButton, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addChestButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteChestButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addTableButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRoomButton)
                    .addComponent(addChestButton)
                    .addComponent(addTableButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteTableButton)
                    .addComponent(deleteRoomButton)
                    .addComponent(deleteChestButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        createVacuumButton.setText("Create vacuum");
        createVacuumButton.setEnabled(false);
        createVacuumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createVacuumButtonMouseClicked(evt);
            }
        });

        startVacuumButton.setText("Start vacuum");
        startVacuumButton.setEnabled(false);
        startVacuumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startVacuumButtonMouseClicked(evt);
            }
        });

        pauseVacuumButton.setText("Pause vacuum");
        pauseVacuumButton.setEnabled(false);
        pauseVacuumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseVacuumButtonMouseClicked(evt);
            }
        });

        deleteVacuumButton.setText("Delete vacuum");
        deleteVacuumButton.setEnabled(false);
        deleteVacuumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteVacuumButtonMouseClicked(evt);
            }
        });

        stopVacuumButton.setText("Stop vacuum");
        stopVacuumButton.setEnabled(false);
        stopVacuumButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopVacuumButtonMouseClicked(evt);
            }
        });

        loadResultsButton.setText("Load results");
        loadResultsButton.setEnabled(false);
        loadResultsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loadResultsButtonMouseClicked(evt);
            }
        });

        saveResultsButton.setText("Save results");
        saveResultsButton.setEnabled(false);
        saveResultsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveResultsButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(createVacuumButton, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                    .addComponent(deleteVacuumButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(loadResultsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saveResultsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(startVacuumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pauseVacuumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(loadResultsField, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(stopVacuumButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                        .addComponent(saveResultsField, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cleanableAreaText, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cleanPercentText, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createVacuumButton)
                    .addComponent(startVacuumButton)
                    .addComponent(pauseVacuumButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stopVacuumButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteVacuumButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loadResultsButton)
                            .addComponent(loadResultsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveResultsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveResultsButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cleanableAreaText, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cleanPercentText, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outputText, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newHouseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newHouseButtonMouseClicked
        CreateNewHouse c = new CreateNewHouse(this, true);
        c.setVisible(true);
        if (c.create) {
            House temp = new House(c.width, c.height, c.f);
            h.setHouse(temp);
            if (!gui.isVisible()) {
                gui.launch(h.getWalls(), h.getChests(), h.getTableLegs(),
                        h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight());
                addRoomButton.setEnabled(true);
                addChestButton.setEnabled(true);
                addTableButton.setEnabled(true);
                saveHouseButton.setEnabled(true);
                deleteRoomButton.setEnabled(true);
                deleteChestButton.setEnabled(true);
                deleteTableButton.setEnabled(true);
                createVacuumButton.setEnabled(true);
                loadResultsButton.setEnabled(true);
            } else {
                gui.redoAll(h.getWalls(), h.getChests(), h.getTableLegs(),
                        h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight());
            }
            outputText.setText("New house created.");
        }
    }//GEN-LAST:event_newHouseButtonMouseClicked

    private void loadHouseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadHouseButtonMouseClicked
        try {
            House temp = h.getSerializer().deserializeHouse(loadHouseField.getText());
            outputText.setText(loadHouseField.getText() + " loaded in from file.");
            h.setHouse(temp);
            if (!gui.isVisible()) {
                gui.launch(h.getWalls(), h.getChests(), h.getTableLegs(),
                        h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight());
                addRoomButton.setEnabled(true);
                addChestButton.setEnabled(true);
                addTableButton.setEnabled(true);
                saveHouseButton.setEnabled(true);
                deleteRoomButton.setEnabled(true);
                deleteChestButton.setEnabled(true);
                deleteTableButton.setEnabled(true);
                createVacuumButton.setEnabled(true);
                loadResultsButton.setEnabled(true);
            } else {
                gui.redoAll(h.getWalls(), h.getChests(), h.getTableLegs(),
                        h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight());
            }
        } catch (IOException | ClassNotFoundException e) {
            outputText.setText(loadHouseField.getText() + " not found.");
        }
    }//GEN-LAST:event_loadHouseButtonMouseClicked

    private void saveHouseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveHouseButtonMouseClicked
        if (saveHouseButton.isEnabled()) {
            if (saveHouseField.getText().equals("")) {
                outputText.setText("No name given!");
            } else {
                try {
                    h.getSerializer().serializeHouse(h.getHouse(), saveHouseField.getText());
                    outputText.setText(saveHouseField.getText() + " saved to file.");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_saveHouseButtonMouseClicked

    private void deleteTableButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTableButtonMouseClicked
        if (deleteTableButton.isEnabled()) {
            DeleteObject d = new DeleteObject(this, true);
            d.setVisible(true);
            if (d.delete) {
                h.getHouse().getRoom(new Position(0, 0)).removeFurniture(new Position(d.x, d.y));
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Table deleted.");
            }
        }
    }//GEN-LAST:event_deleteTableButtonMouseClicked

    private void deleteChestButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteChestButtonMouseClicked
        if (deleteTableButton.isEnabled()) {
            DeleteObject d = new DeleteObject(this, true);
            d.setVisible(true);
            if (d.delete) {
                h.getHouse().getRoom(new Position(0, 0)).removeFurniture(new Position(d.x, d.y));
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Chest deleted.");
            }
        }
    }//GEN-LAST:event_deleteChestButtonMouseClicked

    private void deleteRoomButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteRoomButtonMouseClicked
        if (deleteTableButton.isEnabled()) {
            DeleteObject d = new DeleteObject(this, true);
            d.setVisible(true);
            if (d.delete) {
                h.getHouse().removeRoom(new Position(d.x, d.y));
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Room deleted.");
            }
        }
    }//GEN-LAST:event_deleteRoomButtonMouseClicked

    private void addTableButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTableButtonMouseClicked
        if (addTableButton.isEnabled()) {
            CreateNewFurniture c = new CreateNewFurniture(this, true);
            c.setVisible(true);
            if (c.create) {
                h.getHouse().getRoom(new Position(0, 0)).addTable(c.x, c.y, c.width, c.height);
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Table added.");
            }
        }
    }//GEN-LAST:event_addTableButtonMouseClicked

    private void addChestButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addChestButtonMouseClicked
        if (addChestButton.isEnabled()) {
            CreateNewFurniture c = new CreateNewFurniture(this, true);
            c.setVisible(true);
            if (c.create) {
                h.getHouse().getRoom(new Position(0, 0)).addChest(c.x, c.y, c.width, c.height);
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Chest added.");
            }
        }
    }//GEN-LAST:event_addChestButtonMouseClicked

    private void addRoomButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRoomButtonMouseClicked
        if (addRoomButton.isEnabled()) {
            CreateNewRoom c = new CreateNewRoom(this, true);
            c.setVisible(true);
            if (c.create) {
                for (Position p : h.getHouse().getRooms().keySet()) {
                    if (c.x == p.getX() && c.y == p.getY()) {
                        outputText.setText("Add room failed: room already at the given location.");
                        return;
                    }
                }

                h.getHouse().addRoom(new Position(c.x, c.y), makeRoom(0.3, c.width, c.height));
                //absolute
//                switch (c.doorPositionCode) {
//                    case 1: h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(c.x, c.y), c.doorWidth); break;   //left wall
//                    case 2: h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(c.x + House.WALL_THICKNESS, c.y), c.doorWidth); break;    //top wall
//                    case 3: h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(c.x + c.width - House.WALL_THICKNESS, c.y + House.WALL_THICKNESS), c.doorWidth); break; //right wall
//                    case 4: h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(c.x, c.y + c.height - House.WALL_THICKNESS), c.doorWidth); break;   //bottom wall
//                }
                //relative
                switch (c.doorPositionCode) {
                    case 1:
                        h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(0, 0), c.doorWidth);
                        break;   //left wall
                    case 2:
                        h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(House.WALL_THICKNESS, 0), c.doorWidth);
                        break;    //top wall
                    case 3:
                        h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(c.width - House.WALL_THICKNESS, House.WALL_THICKNESS), c.doorWidth);
                        break; //right wall
                    case 4:
                        h.getHouse().getRoom(new Position(c.x, c.y)).addDoor(new Position(0, c.height - House.WALL_THICKNESS), c.doorWidth);
                        break;   //bottom wall
                }
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Room added.");
            }
        }
    }//GEN-LAST:event_addRoomButtonMouseClicked

    private Room makeRoom(double wallThickness, double roomWidth, double roomHeight) {
        Map<Position, Wall> wallMap = new HashMap<>();
        wallMap.put(new Position(0, 0), new Wall(new CollisionRectangle(wallThickness, (roomHeight - wallThickness))));     //left wall
        wallMap.put(new Position(wallThickness, 0), new Wall(new CollisionRectangle((roomWidth - wallThickness), wallThickness)));   //top wall
        wallMap.put(new Position((roomWidth - wallThickness), wallThickness), new Wall(new CollisionRectangle(wallThickness, (roomHeight - wallThickness))));   //right wall
        wallMap.put(new Position(0, (roomHeight - wallThickness)), new Wall(new CollisionRectangle((roomWidth - wallThickness), wallThickness)));  //bottom wall

        return new Room(wallMap);
    }

    private void createVacuumButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createVacuumButtonMouseClicked
        if (createVacuumButton.isEnabled()) {
            CreateNewVacuum c = new CreateNewVacuum(this, true);
            c.setVisible(true);
            if (c.create) {
                s = new Simulator(h.getHouse(), new Position(c.x, c.y), c.batteryLife, c.vacuumEfficiency, c.whiskerEfficiency, c.vacuumSpeed, c.algorithmCode);
                cleanableAreaText.setText("Cleanable area: " + (int)s.getCleanableArea() + " sq m");
                cleanPercentText.setText("Percent cleaned: " + (int)s.getCleanPercent() + "%");
                deleteVacuumButton.setEnabled(true);
                startVacuumButton.setEnabled(true);
                newHouseButton.setEnabled(false);
                loadHouseButton.setEnabled(false);
                saveHouseButton.setEnabled(false);
                addRoomButton.setEnabled(false);
                deleteRoomButton.setEnabled(false);
                addChestButton.setEnabled(false);
                deleteChestButton.setEnabled(false);
                addTableButton.setEnabled(false);
                deleteTableButton.setEnabled(false);
                createVacuumButton.setEnabled(false);
                saveResultsButton.setEnabled(false);
                gui.redoVacuum(s.getVacuumShape());
                gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
                outputText.setText("Vacuum created.");
            }
        }
    }//GEN-LAST:event_createVacuumButtonMouseClicked

    private void startVacuumButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startVacuumButtonMouseClicked
        if (startVacuumButton.isEnabled()) {
            startVacuumButton.setEnabled(false);
            pauseVacuumButton.setEnabled(true);
            stopVacuumButton.setEnabled(true);
            deleteVacuumButton.setEnabled(false);
            loadResultsButton.setEnabled(false);
            outputText.setText("Vacuum started.");
            moveVacuum = true;
            if (notStartedYet) {
                t1.start();
                notStartedYet = false;
            }
        }
    }//GEN-LAST:event_startVacuumButtonMouseClicked

    private void pauseVacuumButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseVacuumButtonMouseClicked
        if (pauseVacuumButton.isEnabled()) {
            pauseVacuumButton.setEnabled(false);
            startVacuumButton.setEnabled(true);
            deleteVacuumButton.setEnabled(true);
            moveVacuum = false;
            outputText.setText("Vacuum paused.");
        }
    }//GEN-LAST:event_pauseVacuumButtonMouseClicked

    private void deleteVacuumButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteVacuumButtonMouseClicked
        if (deleteVacuumButton.isEnabled()) {
            s = null;
            newHouseButton.setEnabled(true);
            loadHouseButton.setEnabled(true);
            saveHouseButton.setEnabled(true);
            addRoomButton.setEnabled(true);
            deleteRoomButton.setEnabled(true);
            addChestButton.setEnabled(true);
            deleteChestButton.setEnabled(true);
            addTableButton.setEnabled(true);
            deleteTableButton.setEnabled(true);
            createVacuumButton.setEnabled(true);
            loadResultsButton.setEnabled(true);
            startVacuumButton.setEnabled(false);
            deleteVacuumButton.setEnabled(false);
            stopVacuumButton.setEnabled(false);
            saveResultsButton.setEnabled(false);
            gui.redoVacuum(null);
            gui.redo(h.getWalls(), h.getChests(), h.getTableLegs());
            outputText.setText("Vacuum deleted.");
            cleanableAreaText.setText("");
            cleanPercentText.setText("");
        }
    }//GEN-LAST:event_deleteVacuumButtonMouseClicked

    private void stopVacuumButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopVacuumButtonMouseClicked
        if (stopVacuumButton.isEnabled()) {
            stopVacuumButton.setEnabled(false);
            pauseVacuumButton.setEnabled(false);
            startVacuumButton.setEnabled(false);
            deleteVacuumButton.setEnabled(true);
            createVacuumButton.setEnabled(true);
            saveResultsButton.setEnabled(true);
            loadResultsButton.setEnabled(true);
            moveVacuum = false;
            gui.paintCleanSpots(s.getCleanSpots());
        }
    }//GEN-LAST:event_stopVacuumButtonMouseClicked

    private void loadResultsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loadResultsButtonMouseClicked
        if (loadResultsButton.isEnabled()) {
            try {
                Simulator temp = h.getSerializer().deserializeResults(loadResultsField.getText());
                ViewResults v = new ViewResults(this, true);
                v.loadInResults(temp, gui);
                v.setVisible(true);
                if (s != null) {
                    gui.redoVacuum(s.getVacuumShape());
                    gui.redoAll(h.getWalls(), h.getChests(), h.getTableLegs(),
                            h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight()); 
                    gui.paintCleanSpots(s.getCleanSpots());
                }
                else {
                    gui.redoVacuum(null);
                    gui.redoAll(h.getWalls(), h.getChests(), h.getTableLegs(),
                            h.getFloorCode(), (int) h.getHouseWidth(), (int) h.getHouseHeight()); 
                }
            } catch (IOException | ClassNotFoundException e) {
                outputText.setText(loadResultsField.getText() + " not found.");
            }
        }
    }//GEN-LAST:event_loadResultsButtonMouseClicked

    private void saveResultsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveResultsButtonMouseClicked
        if (saveResultsButton.isEnabled()) {
            if (saveResultsField.getText().equals("")) {
                outputText.setText("No name given!");
            } else {
                try {
                    h.getSerializer().serializeResults(s, saveResultsField.getText());
                    outputText.setText(saveResultsField.getText() + " saved to file.");
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_saveResultsButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HouseEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new HouseEditor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addChestButton;
    private javax.swing.JButton addRoomButton;
    private javax.swing.JButton addTableButton;
    private javax.swing.JLabel cleanPercentText;
    private javax.swing.JLabel cleanableAreaText;
    private javax.swing.JButton createVacuumButton;
    private javax.swing.JButton deleteChestButton;
    private javax.swing.JButton deleteRoomButton;
    private javax.swing.JButton deleteTableButton;
    private javax.swing.JButton deleteVacuumButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton loadHouseButton;
    private javax.swing.JTextField loadHouseField;
    private javax.swing.JButton loadResultsButton;
    private javax.swing.JTextField loadResultsField;
    private javax.swing.JButton newHouseButton;
    private javax.swing.JLabel outputText;
    private javax.swing.JButton pauseVacuumButton;
    private javax.swing.JButton saveHouseButton;
    private javax.swing.JTextField saveHouseField;
    private javax.swing.JButton saveResultsButton;
    private javax.swing.JTextField saveResultsField;
    private javax.swing.JButton startVacuumButton;
    private javax.swing.JButton stopVacuumButton;
    // End of variables declaration//GEN-END:variables
}
