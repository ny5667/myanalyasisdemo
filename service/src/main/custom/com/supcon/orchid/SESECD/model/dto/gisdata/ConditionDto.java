package com.supcon.orchid.SESECD.model.dto.gisdata;

import java.util.List;

public class ConditionDto {
	
	private List<String> ids;
    private Conditions conditions;
    private String type;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public static class Conditions{

        private List<String> searchField;
        private String keyword;
        
        public List<String> getSearchField() {
			return searchField;
		}
		public void setSearchField(List<String> searchField) {
			this.searchField = searchField;
		}
		public String getKeyword() {
            return keyword;
        }
        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

    }
}
