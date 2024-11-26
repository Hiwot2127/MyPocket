
package myPackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("Expense Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);


        JPanel outerPanel = new JPanel(new GridBagLayout()); 
        outerPanel.setBorder(new CompoundBorder(new LineBorder(Color.GRAY), new EmptyBorder(10, 10, 10, 10)));

        outerPanel.setBackground(new Color(211, 211, 211)); 
        setContentPane(outerPanel); 

        
JPanel innerPanel = new JPanel(new BorderLayout());
innerPanel.setOpaque(true); 
innerPanel.setBackground(Color.WHITE); 
innerPanel.setPreferredSize(new Dimension(600, 400));


GridBagConstraints gbc = new GridBagConstraints();
gbc.gridx = 0;
gbc.gridy = 0;
outerPanel.add(innerPanel, gbc);


       
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40)); // Add padding
        innerPanel.add(contentPanel, BorderLayout.CENTER);

        
        String logoPath = "C:\\Users\\hp\\OneDrive\\Documents\\NetBeansProjects\\ExpenseTracker\\src\\image\\logo.png";
        ImageIcon logoIcon = new ImageIcon(logoPath);
        int newWidth = 200; 
        int newHeight = 200; 
        Image image = logoIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(image);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(logoLabel);


        JLabel sloganLabel = new JLabel("Track your expenses effortlessly!");
        sloganLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
        sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sloganLabel.setForeground(Color.BLACK);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(sloganLabel);

        
        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStartedButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        getStartedButton.setBackground(new Color(0, 153, 153));
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setBorder(new LineBorder(Color.WHITE, 2));
        getStartedButton.setPreferredSize(new Dimension(150, 40)); 
        getStartedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                myFirstForm signUpPage = new myFirstForm();
                signUpPage.setVisible(true);
                dispose(); 
            }
        });
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(getStartedButton);
    }
/**
 *
 * @author hp
 */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       // Create and show the homepage
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
            }
        });
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
