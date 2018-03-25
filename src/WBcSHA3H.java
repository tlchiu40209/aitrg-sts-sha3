import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest224;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest256;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest384;
import org.bouncycastle.jcajce.provider.digest.SHA3.Digest512;

import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.awt.event.ActionEvent;

public class WBcSHA3H {

	private JFrame frmShaPerformanceTest;
	private JTextField txtFilesize;
	private JComboBox cbxSha3;
	private JTextField txtTimes;
	private JTextField txtElapse;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WBcSHA3H window = new WBcSHA3H();
					window.frmShaPerformanceTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WBcSHA3H() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmShaPerformanceTest = new JFrame();
		frmShaPerformanceTest.setTitle("SHA3 Performance Test");
		frmShaPerformanceTest.setBounds(100, 100, 450, 300);
		frmShaPerformanceTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frmShaPerformanceTest.getContentPane().add(panel,  BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblFilesize = new JLabel("Filesize");
		lblFilesize.setBounds(22, 32, 46, 15);
		panel.add(lblFilesize);
		
		txtFilesize = new JTextField();
		txtFilesize.setBounds(114, 29, 213, 21);
		panel.add(txtFilesize);
		txtFilesize.setColumns(10);
		
		JLabel lblBytes = new JLabel("Bytes");
		lblBytes.setBounds(358, 32, 46, 15);
		panel.add(lblBytes);
		
		JLabel lblSha3 = new JLabel("SHA3:");
		lblSha3.setBounds(22, 78, 46, 15);
		panel.add(lblSha3);
		
		cbxSha3 = new JComboBox();
		cbxSha3.setModel(new DefaultComboBoxModel(new String[] {"SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512"}));
		cbxSha3.setBounds(114, 75, 213, 21);
		panel.add(cbxSha3);
		
		JLabel lblTimes = new JLabel("Times");
		lblTimes.setBounds(22, 128, 46, 15);
		panel.add(lblTimes);
		
		txtTimes = new JTextField();
		txtTimes.setBounds(114, 125, 213, 21);
		panel.add(txtTimes);
		txtTimes.setColumns(10);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread execute = new Thread() {
					public void run() {
						lblStatus.setText("Running");
						int fileSize = Integer.parseInt(txtFilesize.getText());
						int times = Integer.parseInt(txtTimes.getText());
						int selection = cbxSha3.getSelectedIndex();
						
						SecureRandom sr = new SecureRandom();
						
						byte[] original = new byte[fileSize];
						sr.nextBytes(original);
						
						DigestSHA3 sha3 = null;
						switch(selection) {
						case 0:
							sha3 = new Digest224();
							break;
						case 1:
							sha3 = new Digest256();
							break;
						case 2:
							sha3 = new Digest384();
							break;
						case 3:
							sha3 = new Digest512();
							break;
						}
						
						long msbefore;
						long msafter;
						long total = 0;
						
						for (int i = 0; i < times; i++) {
							msbefore = getCurrentTime();
							sha3.update(original);
							msafter = getCurrentTime();
							total = total + (msafter - msbefore);
						}
						
						String result = "AVSH: " + (float) total/times + " ms";
						txtElapse.setText(result);
						lblStatus.setText("Ready");
						
						
					}
					
				};
				execute.start();
			}
		});
		btnExecute.setBounds(337, 228, 87, 23);
		panel.add(btnExecute);
		
		JLabel lblElapse = new JLabel("Elapse");
		lblElapse.setBounds(22, 176, 46, 15);
		panel.add(lblElapse);
		
		txtElapse = new JTextField();
		txtElapse.setBounds(114, 173, 213, 21);
		panel.add(txtElapse);
		txtElapse.setColumns(10);
		
		lblStatus = new JLabel("Ready");
		lblStatus.setBounds(22, 232, 46, 15);
		panel.add(lblStatus);
	}
	
	public static long getCurrentTime() {
		Date today;
		today = new Date();
		return today.getTime();
	}
	

	public JTextField getTxtFilesize() {
		return txtFilesize;
	}
	public JComboBox getCbxSha3() {
		return cbxSha3;
	}
	public JTextField getTxtTimes() {
		return txtTimes;
	}
	public JTextField getTxtElapse() {
		return txtElapse;
	}
	public JLabel getLblStatus() {
		return lblStatus;
	}
}
