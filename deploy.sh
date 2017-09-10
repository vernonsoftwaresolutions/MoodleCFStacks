#!/bin/bash

set -e

S3_BUCKET=$1
VERSION=$2
ENV=$3
DNSNAME=$4
HOSTEDZONENAME=$5

API_NAME="GetCFStack"

##
# Package record set
##
#aws cloudformation package --template-file \
#    formation_recordset.yaml --output-template-file \
#    formation_recordset_output.yaml --s3-bucket $S3_BUCKET

##
# Deploy record set
##
#aws cloudformation deploy --template-file \
#    formation_recordset_output.yaml --capabilities CAPABILITY_IAM \
#    --stack-name apidnsname --parameter-overrides HostedZoneName="${DNSNAME}" RecordSet="${RecordSet}"
##
# Package API Gateway Assets
##
aws cloudformation package --template-file \
    formation_assets.yaml --output-template-file \
    formation_assets_output.yaml --s3-bucket $S3_BUCKET

##
# Deploy Assets
##
aws cloudformation deploy --template-file \
    formation_assets_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name ${API_NAME}  --parameter-overrides MoodleAPIDNSName="${DNSNAME}" HostedZoneName="${HOSTEDZONENAME}"

##
# get the api gateway ref
##
apiGatewayApiRef=`aws cloudformation describe-stacks \
                    --stack-name "${API_NAME}" --query \
                    "Stacks[0].[Outputs[? starts_with(OutputKey, 'DemoApiGatewayRef')]][0][*].{OutputValue:OutputValue}" \
                    --output text`
echo "api gateway ref ${apiGatewayApiRef}"
##
# get the current deploymentId
##
deploymentId=`aws apigateway get-stages \
                 --rest-api-id "${apiGatewayApiRef}" \
                 --query "[item[?stageName=='Latest']][0][0].deploymentId" \
                 --output text`
echo "deploymentId ${deploymentId}"

##
# Publish new lambda version
##
LAMBDANAME=`aws cloudformation describe-stacks \
            --stack-name "${API_NAME}"  \
            --query "Stacks[0].[Outputs[?starts_with(OutputValue, 'arn:aws:lambda')]][0][*].{OutputValue:OutputValue}" --output=text`

echo "retrieved lambda name ${LAMBDANAME}"
LAMBDAVERSION=`aws lambda publish-version \
                --function-name "${LAMBDANAME}" \
                --description "${VERSION}" \
                --query '{Version:Version}' --output=text`

##
# Package the environment template
##
aws cloudformation package --template-file \
    formation_env.yaml --output-template-file \
    formation_env_output.yaml --s3-bucket $S3_BUCKET

##
# Deploy template
##
echo "about to deploy environment with variables ${apiGatewayApiRef} ${ENV} ${deploymentId} ${LAMBDAVERSION} ${DNSNAME}"

aws cloudformation deploy --template-file \
    formation_env_output.yaml --capabilities CAPABILITY_IAM \
    --stack-name "${API_NAME}-${ENV}" \
    --parameter-overrides ApiGateway="${apiGatewayApiRef}" \
    StageName="${ENV}" DeploymentId="${deploymentId}" DomainName="${DNSNAME}" Version="${LAMBDAVERSION}" DomainName="${DNSNAME}"|| exit 0


