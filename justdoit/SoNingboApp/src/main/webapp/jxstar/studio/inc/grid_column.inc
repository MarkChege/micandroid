config.initpage = function(gridNode){
		dataImportParam: function() {

		setFunColext: function(){
			var records = this.grid.getSelectionModel().getSelections();
			if (!JxUtil.selectone(records)) return;


			//��ʾ����
			Jxstar.showData({
				filename: define.formpage,
				title: define.nodetitle,
				callback: hdcall
			});
		}
	};