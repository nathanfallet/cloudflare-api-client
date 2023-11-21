package me.nathanfallet.cloudflare.repositories.dns.records

import me.nathanfallet.cloudflare.models.dns.records.DNSRecord
import me.nathanfallet.cloudflare.models.dns.records.DNSRecordPayload
import me.nathanfallet.usecases.models.repositories.IChildModelSuspendRepository

interface IDNSRecordsRepository :
    IChildModelSuspendRepository<DNSRecord, String, DNSRecordPayload, DNSRecordPayload, String>
