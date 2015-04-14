package ar.uba.fi.talker.dao;

public class ConversationDAO {

	private int id;
	private String path;
	private String name;
	private String pathSnapshot;
	
	public ConversationDAO() {
	}

	public ConversationDAO(int id, String path, String name, String pathSnapshot) {
		super();
		this.id = id;
		this.path = path;
		this.name = name;
		this.pathSnapshot = pathSnapshot;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPathSnapshot() {
		return pathSnapshot;
	}
	public void setPathSnapshot(String pathSnapshot) {
		this.pathSnapshot = pathSnapshot;
	}
}
