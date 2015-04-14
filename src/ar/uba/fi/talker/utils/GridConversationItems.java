package ar.uba.fi.talker.utils;

public class GridConversationItems {

	private int id;
	private ConversationView conversationView;

	public GridConversationItems(int id, ConversationView conversationView) {
		this.id = id;
		this.conversationView = conversationView;
	}

	public ConversationView getConversationView() {
		return conversationView;
	}

	public int getId() {
		return id;
	}
}
