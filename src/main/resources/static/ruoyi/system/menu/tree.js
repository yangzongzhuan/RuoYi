// 树结构初始化加载
var setting = {view:{selectedMulti:false},data:{key:{title:"title"},simpleData:{enable:true}},
	callback:{onClick:function(event, treeId, treeNode){
		var treeId = treeNode.id;
		var treeName = treeNode.name;
		$("#treeId").val(treeId);
		$("#treeName").val(treeName);
	}}
}, tree, loadTree = function(){
	$.get(ctx + "system/menu/menuTreeData", function(data) {
		var treeName = $("#treeName").val();
	    tree = $.fn.zTree.init($("#tree"), setting, data);
	    // 展开第一级节点
	    var nodes = tree.getNodesByParam("level", 0);
	    for (var i = 0; i < nodes.length; i++) {
	        tree.expandNode(nodes[i], false, false, false);
	        selectCheckNode(treeName,nodes[i]);
	    }
	}, null, null, "正在加载，请稍后...");
};

$(function() {
    loadTree();
});

function selectCheckNode(treeName, node) {
	if(treeName == node.name){
		tree.selectNode(node, true);
	}
}

$('#btnExpand').click(function() {
	tree.expandAll(true);
});
$('#btnCollapse').click(function() {
	tree.expandAll(false);
});

function loadMenu(){
	var treeId = $("#treeId").val();
	var treeName = $("#treeName").val();
	parent.$("#treeId").val(treeId);
	parent.$("#treeName").val(treeName);
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

var lastValue = "", nodeList = [], key = $("#keyword");
key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
key.bind("keydown", function (e){if(e.which == 13){searchNode();}});

function focusKey(e) {
	if (key.hasClass("empty")) {
		key.removeClass("empty");
	}
}

function blurKey(e) {
	if (key.get(0).value === "") {
		key.addClass("empty");
	}
	searchNode(e);
}

function searchNode() {
	var value = $.trim(key.get(0).value);
	var keyType = "name";
	if (lastValue === value) {
		return;
	}
	lastValue = value;
	var nodes = tree.getNodes();
	if (value == "") {
		showAllNode(nodes);
		return;
	}
	hideAllNode(nodes);
	nodeList = tree.getNodesByParamFuzzy(keyType, value);
	updateNodes(nodeList);
}

function hideAllNode(nodes){
	var tree = $.fn.zTree.getZTreeObj("tree");
	nodes = tree.transformToArray(nodes);
	for(var i=nodes.length-1; i>=0; i--) {
		tree.hideNode(nodes[i]);
	}
}

function showAllNode(nodes){
	nodes = tree.transformToArray(nodes);
	for(var i=nodes.length-1; i>=0; i--) {
		if(nodes[i].getParentNode()!=null){
			tree.expandNode(nodes[i],false,false,false,false);
		}else{
			tree.expandNode(nodes[i],true,true,false,false);
		}
		tree.showNode(nodes[i]);
		showAllNode(nodes[i].children);
	}
}

function updateNodes(nodeList) {
	tree.showNodes(nodeList);
	for(var i=0, l=nodeList.length; i<l; i++) {
		var treeNode = nodeList[i];
		showChildren(treeNode);
		showParent(treeNode)
	}
}

function showChildren(treeNode){
	if (treeNode.isParent){
		for(var idx in treeNode.children){
			var node = treeNode.children[idx];
			tree.showNode(node);
			showChildren(node);
		}
	}
}
function showParent(treeNode){
	var parentNode;
	while((parentNode = treeNode.getParentNode()) != null){
		tree.showNode(parentNode);
		tree.expandNode(parentNode, true, false, false);
		treeNode = parentNode;
	}
}

function search($this) {
	$('#search').slideToggle(200);
	$('#btnShow').toggle();
	$('#btnHide').toggle();
	$('#keyword').focus();
}