class FPTClient{
    private FTPClient ftpClient;

    public FPTClient(){
        ftpClient = new FTPClient();
    }

    public boolean Login(String username, String password) {
        try {
            ftpClient.connect("ftp.swfwmd.state.fl.us",21);
            boolean login = ftpClient.login(username, password);
            if (login) {
                System.out.println("Connection established...");
                System.out.println("Status: " + ftpClient.getStatus());
                return true;
            } else {
                return false;
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean Logout() {
        try {
            boolean logout = ftpClient.logout();
            if (logout) {
                System.out.println("Succesfully logged out.");
                return true;
            } else {
                System.out.println("Logout was unsuccessful.");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect(); // disconnect on logout
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean Rename(String oldName, String newName){
        try {
            boolean rename = ftpClient.rename(oldName,newName);
            if(rename) {
                System.out.println(oldName + " has been renamed to " + newName);
                return true;
            } else {
                System.out.println("Failed to rename: " + oldName);
                return false;
            }
        } catch (SocketException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean DeleteFile(String fileName) {
        try {
            boolean deleted = ftpClient.deleteFile(fileName);
            if (deleted) {
                System.out.println("The file was deleted successfully.");
                return true;
            } else {
                System.out.println("Could not delete the file.");
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
	
    public boolean CreateDirectory(String directoryName) {
        String directoryName = directoryName;
        boolean success =ftpClient.makeDirectory(directoryName);
            if(success) {
                System.out.println("Directory " + directoryName +" successfully created");
               return true;    
        } else {
                System.out.println("Failed to create directory");
		return false;
            }
    }

    public void ListDirectoryAndFiles() {
        // lists files and directories in the current working directory
        FTPFile[] files = ftpClient.listFiles();
 
	// print details for every file and directory
	DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
	String nameTitle = "NAME";
	String sizeTitle = "SIZE";
	String dateTitle = "DATE";
	System.out.printf("%35s" + "%35s"  + "%35s" + "\n", nameTitle, sizeTitle, dateTitle );
	for (FTPFile file : files) {
    	    String name = file.getName();
    	    if (file.isDirectory()) {
                name = "[" + name.toUpperCase() + "]";
    	    }
    	    long size = file.getSize();
    	    String date = dateFormater.format(file.getTimestamp().getTime());
    	    System.out.printf("%35s" + "%35d" + "%35s" + "\n", name, size, date);
	}
    }
}


