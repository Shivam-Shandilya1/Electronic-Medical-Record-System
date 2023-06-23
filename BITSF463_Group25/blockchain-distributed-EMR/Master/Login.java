import java.awt.event.*;
import javax.swing.*;
import java.io.*;
// import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Toolkit;

class Login implements ActionListener{
    static String username;
    static int B;
    // static String initial_username_string = "user1";
    // static String initial_password_string = "123";

    JFrame f=new JFrame();

    JLabel l1=new JLabel("Wallet Username");
    JLabel l2=new JLabel("Wallet Password");

    JTextField t1 = new JTextField();
    JTextField t2 = new JTextField();
    
    JButton b1=new JButton("Login");
    JButton b4=new JButton("SignUp");
    JButton b2=new JButton("Quit");
    JButton b3=new JButton("See Ledger");

    Login()
    {
    	// System.out.println(StringUtil.applySha256("user1"));
        // System.out.println(StringUtil.applySha256("234"));
        // System.out.println(StringUtil.applySha256("user2"));
        // System.out.println(StringUtil.applySha256("123"));
        // System.out.println(StringUtil.applySha256("user3"));
        // System.out.println(StringUtil.applySha256("user4"));
        // System.out.println(StringUtil.applySha256("user5"));
        // System.out.println(StringUtil.applySha256("user6"));
        // System.out.println(StringUtil.applySha256("user7"));
        // System.out.println(StringUtil.applySha256("user8"));
        // System.out.println(StringUtil.applySha256("user9"));
        // System.out.println(StringUtil.applySha256("user10"));
    	b3.setBounds(50,30,300,20);
    	
    	l1.setBounds(50,110,300,20);
        t1.setBounds(50,135,300,20);

        l2.setBounds(50,160,300,20);
        t2.setBounds(50,185,300,20);
        
        b1.setBounds(50,220,300,20);
        b4.setBounds(50,250,300,20);
        b2.setBounds(50,300,300,20);
        

        f.add(l1);f.add(l2);f.add(t1);f.add(t2);
        f.add(b1); f.add(b2); f.add(b3);f.add(b4);
        b1.addActionListener(this); b2.addActionListener(this); b3.addActionListener(this);b4.addActionListener(this);
        f.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(400,400);
        f.setLocation((dim.width-f.getWidth())/2,(dim.height - f.getHeight())/2);
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b1 ){
        	// Login button
            if( verifyUserCredentials(t1.getText(), t2.getText()) == true){
                System.out.println("Current User:- " + username);
                new Wallet();
                f.setVisible(false);
            }
            else{
                System.out.println("Invalid "+t1.getText()+" and Password\n.You need to SignUp.");
                f.dispose();
                new Login();
            }
        }
        else if(e.getSource() == b3){
            // ViewLedger Button on Terminal
            BlockChain.viewLedger();
        }
        else if( e.getSource() == b2){
            System.exit(0);
        }else
        {
            try {
            File f3 = new File("Master/userDB.txt");
            BufferedReader buffer1 = new BufferedReader(new FileReader(f3));
            String readLine1 = "";
            int flag = 0;
            while ( (readLine1 = buffer1.readLine()) != null){
                String[] line_seg = readLine1.split(":");

                if(line_seg[0].equals(t1.getText())){
                    System.out.println("Patient Already Exist.\nPlease Try to Login.");
                    flag = 1;
                    buffer1.close();
                    break;
                }
            }
            buffer1.close();
            if(flag==1)return;
        }catch (IOException e2) {
            e2.printStackTrace();
        }
            String uspass = t1.getText()+":"+t2.getText();
            // System.out.println(uspass);
            String hashA = StringUtil.applySha256(t1.getText());
            String hashB = StringUtil.applySha256(t2.getText());
            String line = hashA+","+hashB+",";
            File f = new File("userDB.txt");
            int cnt_line = 0;
            try (BufferedReader buffer = new BufferedReader(new FileReader(f))) {
                String readLine = "";
                while ( (readLine = buffer.readLine()) != null){cnt_line++;}
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            cnt_line++;
            System.out.println(cnt_line);
            line = line.concat(Integer.toString(cnt_line)).concat(",");
            int token_cnt = ((int)Math.pow(2,cnt_line))%(11);
            line = line.concat(Integer.toString(token_cnt));
            try {
                //Specify the file name and path here
                File file = new File("userDB.txt");
                File file2 = new File("Master/userDB.txt");
                //If the file doesn't exist, create a new one
                if (!file.exists()) {
                    file.createNewFile();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
    
                //Create a FileWriter object and set the append mode to true
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                FileWriter fw2 = new FileWriter(file2.getAbsoluteFile(), true);

                //Create a BufferedWriter object to write to the file
                BufferedWriter bw = new BufferedWriter(fw);
                BufferedWriter bw2 = new BufferedWriter(fw2);
                
                //Write the data to the file and append a new line character
                bw.write(line);
                bw2.write(uspass);
                bw.newLine();
                bw2.newLine();
                //Close the BufferedWriter object
                bw.close();
                bw2.close();
                System.out.println("Patient appended to the file successfully.");
    
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static boolean verifyUserCredentials(String a, String b){
        try{
            // System.out.println(a);
            // System.out.println(b);
            // System.out.println(initial_username_string);
            // System.out.println(initial_password_string);
            // if(a.equals(initial_username_string) && b.equals(initial_password_string))
            // {
            //     System.out.println("Yeah!\n");
            //     username = StringUtil.applySha256(initial_username_string);
            //     B = 10;
            //     return true;
            // }
            String hashA = StringUtil.applySha256(a);
            String hashB = StringUtil.applySha256(b);
            
            File f = new File("userDB.txt");
            BufferedReader buffer = new BufferedReader(new FileReader(f));
            String readLine = "";
           
            while ( (readLine = buffer.readLine()) != null){
                String[] line = readLine.split(",");

                if(line[0].equals(hashA) && line[1].equals(hashB)){
                    username = hashA;
                    B = Integer.parseInt(line[3]);
                    buffer.close();
                    return true;
                }else if(!(line[0].equals(hashA)))
                {
                    System.out.println("Incorrect Patient ID\n");
                }else if(!(line[0].equals(hashB)))
                {
                    System.out.println("Incorrect Password\n");
                }
            }
            buffer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String args[]){
        BlockChain.initiateBlockChain();
        new Login();
    }
}