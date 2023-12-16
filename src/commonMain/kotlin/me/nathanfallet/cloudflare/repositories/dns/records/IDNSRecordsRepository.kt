package me.nathanfallet.cloudflare.repositories.dns.records

import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload
import me.nathanfallet.usecases.models.repositories.remote.IChildModelRemoteRepository

interface IDNSRecordsRepository :
    IChildModelRemoteRepository<DNSRecord, String, DNSRecordPayload, DNSRecordPayload, String>
