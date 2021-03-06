/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tft.gui;

import com.mycompany.tft.api.SearchDevice;
import com.mycompany.tft.ctl.Control;
import com.mycompany.tft.objects.Device;
import java.awt.Frame;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author AZAEL
 */
public class DeviceList extends javax.swing.JDialog {

    private static ArrayList<Device> dev;
    private final int mode;
    private Object msg;
    private static DeviceList myself;
    private final DefaultListModel<String> defaultListModel;
    private final workingThread wT;

    /**
     * Creates new form DeviceList
     */
    public DeviceList(java.awt.Frame parent, boolean modal, ArrayList<Device> dev, int mode) {
        super(parent, modal);
        initComponents();
        this.setIconImage(CustomIcon.getIcon());
        jLabel1.setText("");
        myself=this;
        this.mode=mode;
        parent.setEnabled(false);
        this.dev=dev;
        defaultListModel = new DefaultListModel<String>();
        for (Device device : dev) defaultListModel.addElement(device.getName());
        jList1.setModel(defaultListModel);
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        wT = new workingThread();
        if(mode==0)wT.tr.start();
        jList1.setMaximumSize(jList1.getSize());
        this.setVisible(true);
    }
    
    public static DeviceList getInstance(){
        return (myself==null) ? null: myself;
    }
    public void addDev(Device newDev){
        dev.add(newDev);
        defaultListModel.addElement(newDev.getName());
        //jList1.ensureIndexIsVisible(dev.size());
        /*
        jList1.updateUI();
        this.validate();
        this.repaint();
        jList1.paintImmediately(jList1.getVisibleRect());
        this.validate();
        */
    }
    public void searchEnd(){
        wT.searching=false;
        jLabel1.setText("");
        myself=null;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Confirmar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscando");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(79, 79, 79)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int index=jList1.getSelectedIndex();
        if(index!=-1){
            switch (mode){
                case 0: new EmailDialog((Frame) this.getParent(), true, dev.get(index)).setLocationRelativeTo(this);
                        Control.getInstance().saveDevice(dev.get(jList1.getSelectedIndex()));
                         break;
                case 1: Control.getInstance().setKeyDevice(dev.get(jList1.getSelectedIndex()));;
                        break;
                case 2: //((Config) this.getParent()).setDevice(dev.get(jList1.getSelectedIndex()));
                        break;
                default: ;
                        break;
            };
            searchEnd();
            this.dispose();
        } else JOptionPane.showMessageDialog(this, msg);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        ((Frame) this.getParent()).toFront();
        if(mode==0&&wT.tr.isAlive()){
            new cancelThread().tr.start();
            SearchDevice.cancelInquiry(0);
        }
            searchEnd();
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

private class workingThread{
    private int frame=0;
    private boolean searching=true;
    public Thread tr=new Thread(){
        @Override
        public void run() {
            while(searching){
                frame=((frame+1)%3);
                switch(frame){
                    case 0:jLabel1.setText("Buscando.");
                        break;
                    case 1:jLabel1.setText("Buscando..");
                        break;
                    case 2:jLabel1.setText("Buscando...");
                        break;
                }
                try {
                    sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    };
    
    
}
private class cancelThread{
    private int frame=0;
    private boolean searching=true;
    private MainFrame fm=((MainFrame) myself.getParent());
    public Thread tr=new Thread(){
        @Override
        public void run() {
            while(searching){
                frame=((frame+1)%3);
                switch(frame){
                    case 0:fm.setStatusTag("Deteniendo busqueda.");;
                        break;
                    case 1:fm.setStatusTag("Deteniendo busqueda..");
                        break;
                    case 2:fm.setStatusTag("Deteniendo busqueda...");
                        break;
                }
                try {
                    sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    };
    
    
}
}
