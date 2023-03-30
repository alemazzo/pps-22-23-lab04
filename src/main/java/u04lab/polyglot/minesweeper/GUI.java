package u04lab.polyglot.minesweeper;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private static final int NUM_MINES = 10;
    private final Map<JButton, Pair<Integer, Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size, int numMines) {
        this.logics = new LogicsImpl(size, numMines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);

        ActionListener onClick = (e) -> {
            final JButton bt = (JButton) e.getSource();
            final Pair<Integer, Integer> pos = buttons.get(bt);
            this.logics.reveal(new Position(pos.getX(), pos.getY()));
            if (this.logics.hasLost()) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else {
                drawBoard();
            }
            if (this.logics.hasWon()) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton) e.getSource();
                if (bt.isEnabled()) {
                    final Pair<Integer, Integer> pos = buttons.get(bt);
                    // call the logic here to put/remove a flag
                    final var position = new Position(pos.getX(), pos.getY());
                    logics.toggleFlag(position);
                }
                drawBoard();
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb, new Pair<>(i, j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }

    private void quitGame() {
        this.drawBoard();
        for (var entry : this.buttons.entrySet()) {
            // call the logic here
            // if this button is a mine, draw it "*"
            // disable the button
            final var position = new Position(entry.getValue().getX(), entry.getValue().getY());
            final CellStatus cellStatus = logics.getCellStatus(position);
            if (cellStatus.isMine()) {
                entry.getKey().setText("*");
            }
            entry.getKey().setEnabled(false);
        }
    }

    private void drawBoard() {
        for (var entry : this.buttons.entrySet()) {
            // call the logic here
            // if this button is a cell with counter, put the number
            // if this button has a flag, put the flag
            final var position = new Position(entry.getValue().getX(), entry.getValue().getY());
            final CellStatus cellStatus = logics.getCellStatus(position);

            if (cellStatus.isRevealed()) {
                if (cellStatus.isMine()) {
                    entry.getKey().setText("*");
                } else {
                    entry.getKey().setText(String.valueOf(cellStatus.minesAround()));
                }
                entry.getKey().setEnabled(false);
            } else if (cellStatus.isFlagged()) {
                entry.getKey().setText("F");
            } else {
                entry.getKey().setText(" ");
            }
        }
    }

}
