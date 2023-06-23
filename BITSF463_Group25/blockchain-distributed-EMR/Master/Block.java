// These are instance variables of the Data class.
// They are declared as Strings and will hold information
// about a patient's username, their diagnosis, and the timestamp of the diagnosis.
class Data{
	String patientUsername;
	String diagnosis;
	String timestamp;

	public Data(String p, String d, String timestamp){
		this.patientUsername = p;
		this.diagnosis = d;
		this.timestamp = timestamp;
	}
}

class Block extends Data{
	public String hash;
	public String previousHash;
	private int nonce;

	public Block(String p, String d, String timestamp, String previousHash){
		super(p, d, timestamp);
		this.previousHash = previousHash;
		this.hash = calculateHash();
	}

	public String calculateHash(){
		String calculatedHash = StringUtil.applySha256(
									previousHash +
									patientUsername + diagnosis + timestamp +
									Integer.toString(nonce)
								);
		return calculatedHash;
	}

	//the mineBlock() method is used to mine a new block by
	// finding a hash value that meets a certain criteria, 
	//in this case, a hash with a certain number of leading
	// zeros, determined by the difficulty parameter. 
	//The method uses a while loop to increment the nonce 
	//variable and calculate a new hash for the block until 
	//the required number of leading zeros is achieved.
	//The final hash value is then printed to the console.
	public void mineBlock(int difficulty){
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring(0, difficulty).equals(target)){
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined! : " + hash);
	}
}