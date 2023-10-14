#!/usr/bin/env node
import 'source-map-support/register';
import {FairyRdsDatabaseStack} from '../lib/database-stack/fairy-rds-database-stack'
import * as cdk from 'aws-cdk-lib';
import {App} from "aws-cdk-lib";

const app: App = new cdk.App();

new FairyRdsDatabaseStack(app, 'fairy-rds-database', {
    env: {region: 'eu-central-1', account: '171038610508'}
})
