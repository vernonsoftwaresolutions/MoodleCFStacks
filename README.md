Service to retrieve all stacks given a tag

`curl https://cfstack.vssdevelopment.com/dev/moodle/stacks?tagKey=TYPE&tagValue=TENANT `

Output:

`[{
	"stackId": "arn:aws:cloudformation:us-east-1:417615409974:stack/eze2fc36pbny7gb9/a686d510-9d9d-11e7-8c36-50d5ca63268e",
	"stackName": "eze2fc36pbny7gb9",
	"changeSetId": null,
	"description": null,
	"parameters": [{
		"parameterKey": "ClientName",
		"parameterValue": "eze2fc36pbny7gb9",
		"usePreviousValue": null
	}, {
		"parameterKey": "HostedZoneName",
		"parameterValue": "vssdevelopment.com",
		"usePreviousValue": null
	}, {
		"parameterKey": "VpcId",
		"parameterValue": "vpc-c7aa77be",
		"usePreviousValue": null
	}, {
		"parameterKey": "Priority",
		"parameterValue": "87",
		"usePreviousValue": null
	}, {
		"parameterKey": "ecscluster",
		"parameterValue": "moodle-ecs-single-ECSCluster-13FFIJ0L4OAX4",
		"usePreviousValue": null
	}, {
		"parameterKey": "ecslbhostedzoneid",
		"parameterValue": "Z35SXDOTRQ7X7K",
		"usePreviousValue": null
	}, {
		"parameterKey": "alblistener",
		"parameterValue": "arn:aws:elasticloadbalancing:us-east-1:417615409974:listener/app/ECSLB/d83355199c39c4d2/1f122081b28d79e6",
		"usePreviousValue": null
	}, {
		"parameterKey": "ecslbdnsname",
		"parameterValue": "ECSLB-370931853.us-east-1.elb.amazonaws.com",
		"usePreviousValue": null
	}, {
		"parameterKey": "ecslbarn",
		"parameterValue": "arn:aws:elasticloadbalancing:us-east-1:417615409974:loadbalancer/app/ECSLB/d83355199c39c4d2",
		"usePreviousValue": null
	}],
	"creationTime": 1505868609158,
	"lastUpdatedTime": null,
	"stackStatus": "CREATE_COMPLETE",
	"stackStatusReason": null,
	"disableRollback": false,
	"notificationARNs": [],
	"timeoutInMinutes": null,
	"capabilities": ["CAPABILITY_IAM"],
	"outputs": [],
	"roleARN": null,
	"tags": [{
		"key": "TYPE",
		"value": "TENANT"
	}]
}]`
