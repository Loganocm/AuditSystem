import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Voyage, AuditLog } from '../types';

// Determine API base URL from env or fall back to deployed Render URL
const API_BASE = import.meta.env.VITE_API_URL || 'https://auditsystem-jwb6.onrender.com';
const api = axios.create({ baseURL: API_BASE });

export const VoyageDashboard: React.FC = () => {
    const [voyages, setVoyages] = useState<Voyage[]>([]);
    const [logs, setLogs] = useState<AuditLog[]>([]);
    const [newShip, setNewShip] = useState('');

    const fetchData = async () => {
        const voyageRes = await api.get('/api/voyages');
        const logRes = await api.get('/api/audit-logs');
        setVoyages(voyageRes.data);
        setLogs(logRes.data);
    };

    useEffect(() => { fetchData(); }, []);

    const handleCreate = async () => {
        await api.post('/api/voyages', {
            shipName: newShip,
            departurePort: 'Port Canaveral',
            departureDate: new Date().toISOString().split('T')[0],
            status: 'Scheduled'
        });
        setNewShip('');
        fetchData(); // Refresh to show new voyage AND new audit log
    };

    return (
        <div className="p-6 max-w-4xl mx-auto">
            <h1 className="text-3xl font-bold mb-6 text-blue-900">VoyageVista Manager</h1>
            
            {/* Voyage Creation Section */}
            <div className="mb-8 bg-gray-100 p-4 rounded-lg">
                <h2 className="text-xl font-semibold mb-2">Create New Voyage</h2>
                <div className="flex gap-2">
                    <input 
                        className="border p-2 rounded flex-grow"
                        value={newShip}
                        onChange={(e) => setNewShip(e.target.value)}
                        placeholder="Enter Ship Name (e.g. Disney Wish)"
                    />
                    <button 
                        onClick={handleCreate}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    >
                        Create Voyage
                    </button>
                </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* Active Voyages List */}
                <div>
                    <h2 className="text-xl font-semibold mb-4">Active Voyages</h2>
                    <ul className="space-y-2">
                        {voyages.map(v => (
                            <li key={v.id} className="border p-3 rounded shadow-sm flex justify-between">
                                <span>{v.shipName}</span>
                                <span className="text-sm bg-green-100 text-green-800 px-2 py-1 rounded">{v.status}</span>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Audit Log Stream (The "Disney Requirement" Feature) */}
                <div>
                    <h2 className="text-xl font-semibold mb-4">System Audit Traceability</h2>
                    <div className="bg-slate-900 text-green-400 p-4 rounded h-64 overflow-y-auto font-mono text-sm">
                        {logs.map(log => (
                            <div key={log.id} className="mb-2 border-b border-slate-700 pb-1">
                                <span className="text-xs text-slate-500">{new Date(log.timestamp).toLocaleString()}</span><br/>
                                <span className="font-bold">[{log.action}]</span> {log.details}
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
};
