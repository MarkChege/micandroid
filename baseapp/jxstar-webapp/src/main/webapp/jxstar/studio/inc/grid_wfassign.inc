	//���������һ�а�ť
	cols[cols.length] = {col:
		{header:'', width:50, xtype:'actioncolumn', menuDisabled:true, align:'center', items:[{
				icon: 'resources/images/icons/button/chkw.gif',
				tooltip: jx.wf.execheck,	//'ִ����������'
				handler: function(grid, rowIndex, colIndex) {
					var rec = grid.getStore().getAt(rowIndex);
					var taskId = rec.get('wf_assign__task_id');
					var instanceId = rec.get('wf_assign__instance_id');
					var funId = rec.get('wf_assign__fun_id');
					var dataId = rec.get('wf_assign__data_id');
					
					var assignData = {taskId:taskId, instanceId:instanceId, funId:funId, dataId:dataId};
					JxUtil.showCheckWindow(assignData, 'check_work');
				}
			}]
		}
	};

	//�����������ֶ�ֵ��Ϊ������
	var renderTask = function(val, metaData, record) {
		var funId = record.get('wf_assign__fun_id');
		var dataId = record.get('wf_assign__data_id');
		var userId = record.get('wf_assign__assign_userid');
		
		var chgcolor = 'onmouseover="this.style.color=\'#FF4400\';" onmouseout="this.style.color=\'#0080FF\';"';
		var html = '<a href="javascript:void(0)" style=\'color:#0080FF;\' '+ chgcolor +' onclick="JxUtil.showCheckData(\''+ funId +'\', \''+ dataId +'\', \''+ userId +'\');">'+ val +'</a>';
		return html;
	};
	
	//�ѵ�һ�е�ֵ��Ϊ������
	cols[0].col.renderer = renderTask;
	
	//����Ҫ��ѡģʽ
	config.param.selectModel = 'row';