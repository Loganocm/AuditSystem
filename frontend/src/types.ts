export interface Voyage {
    id?: number;
    shipName: string;
    departurePort: string;
    destinationPort?: string;
    departureDate: string;
    returnDate?: string;
    status: string;
}

export interface AuditLog {
    id: number;
    entityName: string;
    action: string;
    timestamp: string;
    details: string;
}
