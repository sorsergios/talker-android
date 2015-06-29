package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.dto.TalkerDTO;

public class ConversationDAO extends TalkerDTO {

	private String path;
	private String name;
	private String pathSnapshot;
	
	public ConversationDAO() {
	}

	public ConversationDAO(long id, String path, String name, String pathSnapshot) {
		super();
		this.setId(id);
		this.path = path;
		this.name = name;
		this.pathSnapshot = pathSnapshot;
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
