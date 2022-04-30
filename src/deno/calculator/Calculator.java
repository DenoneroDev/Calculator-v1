package deno.calculator;

import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class Calculator {

    JTextField  output, calculation;
    JButton btnC, btnBack, btnDiv, btnMul, btnPlusMinus, btnComma, btnSub, btnAdd, btnEqu, btnCopy;
    int ButtonWidth = 80;
    int ButtonHeight = 70;
    JFrame window;
    String font = Font.DIALOG;
    int marginX = 20;
    int marginY = 60;
    String savedOutput = "";
    int[] x = {marginX, marginX + 90, 200, 290, 380};
    int[] y = {marginY, marginY + 100, marginY + 180, marginY + 260, marginY + 340, marginY + 420};
    String appdata = System.getenv("APPDATA");
    ImageIcon copy = new ImageIcon(appdata + "\\deno\\calculator\\copy.png");
    ImageIcon  logo = new ImageIcon(appdata + "\\deno\\calculator\\logo.png");

    public Calculator(String title) {

        window = new JFrame(title);
        window.setSize(405, 600);
        window.getContentPane().setBackground(Color.decode("#1f1f1f"));
        window.setLocationRelativeTo(null);

        calculation = new JTextField(" ");
        calculation.setLocation(x[0], 20);
        calculation.setSize(350, 30);
        calculation.setEditable(false);
        calculation.setBackground(Color.decode("#1f1f1f"));
        calculation.setBorder(null);
        calculation.setForeground(Color.decode("#ffffff"));

        window.add(calculation);

        output = new JTextField("0");
        output.setBounds(x[0], y[0], 350, 70);
        output.setBackground(Color.decode("#363636"));
        output.setFont(new Font(font, Font.PLAIN, 33));
        output.setEnabled(false);
        output.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        window.add(output);

        btnC = button("C", x[0], y[1]);
        btnC.addActionListener(e -> {

            output.setText("0");
            calculation.setText(" ");

        });
        btnBack = button("<-", x[1],y[1]);
        btnBack.addActionListener(e -> {

            if(!output.getText().equalsIgnoreCase("0")) {

                StringBuilder text = new StringBuilder(output.getText());
                output.setText(text.substring(0, text.length() - 1));

                if(output.getText().length() == 1 && output.getText().charAt(0) == '-' || output.getText().length() == 0) {
                    output.setText("0");
                }

                StringBuilder calcText = new StringBuilder(output.getText());
                calculation.setText(calcText.substring(0, calcText.length() - 1));

                if(calculation.getText().length() == 1 && calculation.getText().charAt(0) == '-' || calculation.getText().length() == 0) {
                    calculation.setText(" ");
                }

            }

        });
        btnDiv = button("/", x[2], y[1]);
        btnDiv.addActionListener(e -> {

            char lastChar = calculation.getText().charAt(calculation.getText().length() - 1);

            if(lastChar != ' ' && lastChar != '+' && lastChar != '-' && lastChar != 'x' && lastChar != '/') {
                calculation.setText(calculation.getText() + " / ");
            }

        });
        btnMul = button("x", x[3], y[1]);
        btnMul.addActionListener(e -> {

            char lastChar = calculation.getText().charAt(calculation.getText().length() - 1);

            if(lastChar != ' ' && lastChar != '+' && lastChar != '-' && lastChar != 'x' && lastChar != '/') {
                calculation.setText(calculation.getText() + " x ");
            }

        });
        button("7", x[0], y[2], input());
        button("8", x[1], y[2], input());
        button("9", x[2], y[2], input());

        btnSub = button("-", x[3], y[2]);
        btnSub.addActionListener(e -> {

            char lastChar = calculation.getText().charAt(calculation.getText().length() - 1);

            if(lastChar != ' ' && lastChar != '+' && lastChar != '-' && lastChar != 'x' && lastChar != '/') {
                calculation.setText(calculation.getText() + " - ");
            }

        });

        button("4", x[0], y[3], input());
        button("5", x[1], y[3], input());
        button("6", x[2], y[3], input());

        btnAdd = button("+", x[3], y[3]);
        btnAdd.addActionListener(e -> {

            output.setText("0");

            char lastChar = calculation.getText().charAt(calculation.getText().length() - 1);

            if(lastChar != ' ' && lastChar != '+' && lastChar != '-' && lastChar != 'x' && lastChar != '/') {
                calculation.setText(calculation.getText() + " + ");
            }

        });

        button("1", x[0], y[4], input());
        button("2", x[1], y[4], input());
        button("3", x[2], y[4], input());

        btnEqu = button("=", x[3], y[4]);
        btnEqu.addActionListener(e -> {

            output.setText(calc(calculation.getText()));

        });

        btnPlusMinus = button("+/-", x[0], y[5]);
        btnPlusMinus.addActionListener(e -> {

            if(output.getText().charAt(0) == '0')
                return;

            String txt = (output.getText().charAt(0) != '-') ? "-" + output.getText() : output.getText().replace("-", "");
            output.setText(txt);

            String lastChar;

            lastChar = calculation.getText().replaceFirst(" ", "").split(" ")[calculation.getText().replaceFirst(" ", "").split(" ").length - 1];
            System.out.println(lastChar);

            String CalcTxt = (lastChar.contains("-")) ? calculation.getText().substring(0, calculation.getText().length() - 2) + lastChar.replace("-", "") : calculation.getText().substring(0, calculation.getText().length() - 2) + " -" + lastChar;

            if(calculation.getText().equalsIgnoreCase(" "))
                CalcTxt = "-0";

            calculation.setText(CalcTxt);

        });

        button("0", x[1], y[5], input());

        btnComma = button(".", x[2], y[5]);
        btnComma.addActionListener(e -> {

            if(!output.getText().contains("."))
                output.setText(output.getText() + ".");

        });

        window.setIconImage(logo.getImage());

        btnCopy = button(x[3], y[5], copy);
        btnCopy.addActionListener(e -> {

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String mime = DataFlavor.stringFlavor.getMimeType();
            DataHandler contents = new DataHandler(output.getText(), mime);

            clipboard.setContents(contents, null);

        });

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setResizable(false);
        window.setVisible(true);

    }
    private JButton button(Object text, int x, int y, ActionListener e, ImageIcon img) {

        JButton btn = (text instanceof String) ? new JButton(text.toString()) : new JButton((ImageIcon) text);
        final Border[] border = new Border[1];

        btn.setBackground(Color.decode("#333333"));
        btn.setForeground(Color.decode("#ffffff"));
        btn.setBounds(x, y, ButtonWidth, ButtonHeight);
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 33));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);
        btn.setOpaque(false);
        if(e != null)
            btn.addActionListener(e);
        btn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                btn.setBackground(Color.decode("#1c1c1c"));

            }
            @Override
            public void mousePressed(MouseEvent e) {

                btn.setBackground(Color.decode("#242424"));

            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {

                btn.setBackground(Color.decode("#292929"));
                border[0] = btn.getBorder();
                btn.setBorder(null);

            }
            @Override
            public void mouseExited(MouseEvent e) {

                btn.setBackground(Color.decode("#333333"));
                btn.setBorder(border[0]);

            }

        });
        window.add(btn);
        return btn;

    }
    private String calc(String text) {

        double result = 0;
        CopyOnWriteArrayList<String> StringCalc = new CopyOnWriteArrayList<>(Arrays.asList(text.replaceFirst(" ", "").split(" ")));
        System.out.println(StringCalc);
        double num = 0;

        for(int i = StringCalc.size(); i > 0; i = StringCalc.size()) {

            System.out.println("STELLE: " + StringCalc.get(0));

            switch(StringCalc.get(0)) {

                case "+":

                    result = num + Double.parseDouble(StringCalc.get(0 + 1));
                    num = result;
                    StringCalc.remove(StringCalc.get(0));
                    if(StringCalc.size() >= 1)
                        StringCalc.remove(StringCalc.get(0));

                    break;
                case "-":

                    result = num - Double.parseDouble(StringCalc.get(0 + 1));
                    num = result;
                    StringCalc.remove(StringCalc.get(0));
                    if(StringCalc.size() >= 1)
                        StringCalc.remove(StringCalc.get(0));

                    break;
                case "x":

                    result = num * Double.parseDouble(StringCalc.get(0 + 1));
                    num = result;
                    StringCalc.remove(StringCalc.get(0));
                    if(StringCalc.size() >= 1)
                        StringCalc.remove(StringCalc.get(0));

                    break;
                case "/":

                    result = num / Double.parseDouble(StringCalc.get(0 + 1));
                    num = result;
                    StringCalc.remove(StringCalc.get(0));
                    if(StringCalc.size() >= 1)
                        StringCalc.remove(StringCalc.get(0));

                    break;

                default:

                    num = Double.parseDouble(StringCalc.get(0));
                    StringCalc.remove(StringCalc.get(0));

                    break;
            }

        }
        System.out.println(result);
        return String.valueOf(result);

    }
    private JButton button(Object text, int x, int y) {

        return button(text, x, y, null, null);

    }
    private JButton button(Object text, int x, int y, ActionListener e) {

        return button(text, x, y, e, null);

    }
    private ImageButton button(int x, int y, ImageIcon img) {

        ImageButton btn = new ImageButton(img);

        btn.setToolTipText("Copy");
        btn.setBackground(Color.decode("#333333"));
        btn.setForeground(Color.decode("#ffffff"));
        btn.setBounds(x, y, ButtonWidth, ButtonHeight);
        btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 33));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);

        window.add(btn);
        return btn;

    }
    private ActionListener input() {

        ActionListener e = event -> {

            String txt = output.getText();
            String calcTxt = calculation.getText();

            if(!output.getText().contains(".")) {

                txt = (output.getText().charAt(0) == '0' || output.getText().charAt(0) == '-') ? output.getText().replace("0", "") : output.getText();
                calcTxt = (calculation.getText().charAt(0) == '0' || output.getText().charAt(0) == '-') ? calculation.getText().replace("0", "") : calculation.getText();;

            } else {

                calcTxt = calcTxt + output.getText();

            }

            output.setText(txt + event.getActionCommand());
            calculation.setText(calcTxt + event.getActionCommand());

        };

        return e;


    }
    public static void main(String[] args) {

        new Calculator("Calculator");

    }

}