import * as cdk from 'aws-cdk-lib'
import {Stack} from 'aws-cdk-lib'
import {
    InstanceClass,
    InstanceSize,
    InstanceType,
    Peer,
    Port,
    SecurityGroup,
    SubnetType,
    Vpc
} from "aws-cdk-lib/aws-ec2";

import {
    Credentials,
    DatabaseInstance,
    DatabaseInstanceEngine,
    DatabaseSecret,
    MysqlEngineVersion
} from "aws-cdk-lib/aws-rds";

export class FairyRdsDatabaseStack extends Stack {

    constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
        super(scope, id, props)

        const port: number = 3306;

        const instanceIdentifier: string = 'fairy-mysql'
        const credsSecretName: string = `/${id}/rds/creds/${instanceIdentifier}`.toLowerCase()
        const creds: DatabaseSecret = new DatabaseSecret(this, 'fairy-mysql-credentials', {
            secretName: credsSecretName,
            username: 'admin'
        })

        const vpc: Vpc = new Vpc(this, 'fairy-vpc', {
            subnetConfiguration: [{
                cidrMask: 24,
                name: 'ingress',
                subnetType: SubnetType.PUBLIC,
            }, {
                cidrMask: 24,
                name: 'compute',
                subnetType: SubnetType.PRIVATE_WITH_EGRESS,
            }, {
                cidrMask: 28,
                name: 'rds',
                subnetType: SubnetType.PRIVATE_ISOLATED,
            }]
        })

        const securityGroup: SecurityGroup = new SecurityGroup(this, 'fairy-database-security-group', {
            securityGroupName: `${id}-mysql-database`,
            vpc,
            allowAllOutbound: true
        })

        new DatabaseInstance(this, 'fairy-database-instance', {
            vpcSubnets: {
                onePerAz: true,
                subnetType: SubnetType.PRIVATE_ISOLATED
            },
            credentials: Credentials.fromSecret(creds),
            vpc,
            port: port,
            databaseName: 'fairy',
            allocatedStorage: 20,
            availabilityZone: 'eu-central-1a',
            securityGroups: [securityGroup],
            instanceIdentifier,
            storageEncrypted: true,
            engine: DatabaseInstanceEngine.mysql({
                version: MysqlEngineVersion.VER_8_0
            }),
            instanceType: InstanceType.of(InstanceClass.T2, InstanceSize.LARGE)
        })

    }
}
