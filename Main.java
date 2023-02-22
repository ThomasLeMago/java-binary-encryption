package me.x708a.encryption;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
	private JTextField textField;
    private JButton button1, button2;

    private Map<String, String> bitMapEncode = new  HashMap<String, String>();
    private Map<String, String> bitMapDecode = new  HashMap<String, String>();

    public static JFrame f = new JFrame();
    
    public static HashMap<String, String> initBitMapEncode(HashMap<String, String> bitMap) throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\map.txt");
        BufferedReader br;

        try{
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                if (st != "" && !st.trim().isEmpty()) {
                    bitMap.put(st.split(": ")[0], st.split(": ")[1]);
                }
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitMap;

    }

    public static HashMap<String, String> initBitMapDecode(HashMap<String, String> bitMap) throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\map.txt");
        BufferedReader br;

        try{
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                if (st != "" && !st.trim().isEmpty()) {
                    bitMap.put(st.split(": ")[1], st.split(": ")[0]);
                }
                
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitMap;

    }

    public Main() throws IOException {

        
        bitMapEncode = initBitMapEncode((HashMap<String, String>) bitMapEncode);
        bitMapDecode = initBitMapDecode((HashMap<String, String>) bitMapDecode);

        // Set up the frame
        f.setTitle("Encryption");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 200);
        f.setLocationRelativeTo(null);

        // Create the input field and buttons
        textField = new JTextField(20);
        button1 = new JButton("Encode");
        button2 = new JButton("Decode");

        // Set up the layout
        f.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(button1);
        panel.add(button2);
        f.add(panel, BorderLayout.CENTER);

        // Add event listeners for the buttons
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String encode = "";
                String[] msg = textField.getText().split("");

                for (int i = 0; i < msg.length; i++) {
                    encode += bitMapEncode.get(msg[i]) + "\n";
                }

                FileWriter fileWriter;
                try {
                	File outputFile = new File(System.getProperty("user.dir") + "\\encoded.txt");
                    outputFile.createNewFile();
                	
                	fileWriter = new FileWriter(outputFile.getAbsolutePath());
                    fileWriter.write(encode);
                    fileWriter.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                

                JOptionPane.showMessageDialog(null, "Encoded!");
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String output = "";
                String[] bitList;
                bitList = textField.getText().split(" ");

                for (String bit : bitList) {
                    output += bitMapDecode.get(bit);
                }

                FileWriter fileWriter;
                try {
                	File outputFile = new File(System.getProperty("user.dir") + "\\decoded.txt");
                    outputFile.createNewFile();
                    
                    fileWriter = new FileWriter(outputFile.getAbsolutePath());
                    fileWriter.write(output);
                    fileWriter.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                

                JOptionPane.showMessageDialog(null, "Decoded!");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        // Create and show the GUI
        Main app = new Main();
        f.setVisible(true);
    }
}
